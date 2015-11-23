package jokatu.components.controllers.game;

import jokatu.components.config.FactoryConfiguration.GameFactories;
import jokatu.components.dao.GameDao;
import jokatu.game.Game;
import jokatu.game.GameID;
import jokatu.game.event.EventHandler;
import jokatu.game.event.GameEvent;
import jokatu.game.exception.GameException;
import jokatu.game.factory.game.GameFactory;
import jokatu.game.factory.input.InputDeserialiser;
import jokatu.game.factory.player.PlayerFactory;
import jokatu.game.input.Input;
import jokatu.game.input.UnacceptableInputException;
import jokatu.game.joining.CannotJoinGameException;
import jokatu.game.player.Player;
import jokatu.game.viewresolver.ViewResolver;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.*;

import static java.text.MessageFormat.format;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author Steven Weston
 */
@Controller
public class GameController {

	public static final String GAME_LIST_MAPPING = "/games";

	private final GameFactories gameFactories;
	private final GameDao gameDao;
	private final SimpMessagingTemplate template;
	private final Collection<EventHandler> eventHandlers;

	@Autowired
	public GameController(
			GameFactories gameFactories,
			GameDao gameDao,
			SimpMessagingTemplate template,
			Collection<EventHandler> eventHandlers
	) {
		this.gameFactories = gameFactories;
		this.gameDao = gameDao;
		this.template = template;
		this.eventHandlers = eventHandlers;
	}

	@RequestMapping(GAME_LIST_MAPPING)
	ModelAndView games() {
		// Pro tip: Never give the view the same name as an attribute in the model if you want to use that attribute.
		Map<String, Object> model = new HashMap<>();

		SortedSet<Game<?, ?>> games = new TreeSet<>((g, h) -> g.getIdentifier().compareTo(h.getIdentifier()));
		games.addAll(gameDao.getAll().getUnmodifiableInnerSet());
		model.put("games", games);

		model.put("gameNames", gameFactories.getGameNames());

		return new ModelAndView("views/game_list", model);
	}

	@RequestMapping("/game/{identity}")
	ModelAndView game(@PathVariable("identity") GameID identity, Principal principal) throws GameException {
		Game<Player, ?> game = gameDao.read(identity);
		if (game == null) {
			return new ModelAndView(new RedirectView(GAME_LIST_MAPPING));
		}
		ViewResolver<?, ?> viewResolver = gameFactories.getViewResolver(game);
		Player player = getPlayer(principal, game);
		if (game.hasPlayer(player)) {
			return viewResolver.getViewForPlayer(player);
		} else {
			return viewResolver.getViewForObserver();
		}
	}

	/**
	 * Users can subscribe to public events for a game without being a player.  Examples of public events would include:
	 * <ul>
	 *     <li>Players joining the game</li>
	 *     <li>Players leaving the game</li>
	 *     <li>Results of dice rolls</li>
	 *     <li>The results of the end of the game</li>
	 * </ul>
	 */
	@SubscribeMapping("/public/game/{identity}")
	void publicGameSubscription(
			@DestinationVariable("identity") GameID identity
	) throws GameException {
		getGame(identity, "You cannot subscribe to a non-existent game.");
	}

	@SubscribeMapping("/status/game/{identity}")
	void gameStatusSubscription(
			@DestinationVariable("identity") GameID identity
	) throws GameException {
		getGame(identity, "You cannot subscribe to a non-existent game.");
	}

	@NotNull
	private Game<Player, Input> getGame(
			GameID identity,
			@NotNull final String errorMessage
	) throws GameException {

		Game<Player, Input> game = gameDao.read(identity);
		if (game == null) {
			throw new GameException(
					identity,
					format("Game with ID {0} does not exist.  {1}", identity, errorMessage)
			);
		}
		return game;
	}

	@MessageMapping("/input/game/{identity}")
	void input(@DestinationVariable("identity") GameID identity, @Payload String json, Principal principal)
			throws GameException {

		Game<Player, Input> game = getGame(identity, "You can't input to a game that does not exist.");

		Player player = getPlayer(principal, game);
		if (!game.hasPlayer(player)) {
			throw new UnacceptableInputException(identity, "You can't input to a game you're not playing.");
		}

		InputDeserialiser inputDeserialiser = gameFactories.getInputDeserialiser(game);
		Input input = inputDeserialiser.deserialise(json);
		game.accept(input, player);
	}

	@RequestMapping(value = "/createGame.do", method = POST)
	@ResponseBody
	Game createGame(@RequestParam("gameName") String gameName) {

		GameFactory factory = gameFactories.getFactory(gameName);
		Game<?, ?> game = factory.produce();

		game.observe(event -> sendEvent(game, event));
		return game;
	}

	@RequestMapping(value = "/joinGame.do", method = POST)
	@ResponseBody
	Game<?, ?> join(@RequestParam("gameID") GameID identity, Principal principal) throws GameException {

		Game<Player, ?> game = getGame(identity, "You cannot join a non-existent game.");
		Player player = getPlayer(principal, game);
		if (game.hasPlayer(player)) {
			throw new CannotJoinGameException(
					identity,
					format("You can''t join a game twice!  Use a different account.")
			);
		}
		game.join(player);

		return game;
	}

	/**
	 * Users are allowed to subscribe to a private channel for a game, but unless they're players, they probably won't
	 * receive any messages for it.
	 */
	@SubscribeMapping("/user/{username}/game/{identity}")
	void join(
			@DestinationVariable("username") String username,
			@DestinationVariable("identity") GameID identity,
			Principal principal
	) throws GameException {

		if (username == null || !username.equals(principal.getName())) {
			throw new BadCredentialsException("Logged-in user did not match attempted subscription.");
		}

		getGame(identity, "You cannot subscribe to a non-existent game.");
	}

	private void sendEvent(@NotNull Game game, @NotNull GameEvent event) {
		eventHandlers.forEach(eventHandler -> eventHandler.handle(game, event));
	}

	@NotNull
	private Player getPlayer(@NotNull Principal principal, @NotNull Game<Player, ?> game) throws GameException {
		PlayerFactory factory = gameFactories.getPlayerFactory(game);
		return factory.produce(principal.getName());
	}

	@MessageExceptionHandler(GameException.class)
	void handleException(GameException e, Message originalMessage, Principal principal) {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(originalMessage);
		StompCommand stompCommand = accessor.getCommand();
		switch (stompCommand) {
			case SUBSCRIBE:
				handleSubscriptionError(e, accessor, principal);
				break;
			default:
				template.convertAndSendToUser(principal.getName(), "/errors/" + e.getId(), e);
		}
	}

	private void handleSubscriptionError(GameException e, StompHeaderAccessor accessor, Principal principal) {
		String subscriptionId = accessor.getNativeHeader("id").get(0);

		Map<String, Object> errorHeaders = new HashMap<>();
		errorHeaders.put("subscription-id", subscriptionId);

		template.convertAndSendToUser(principal.getName(), "/errors/" + e.getId(), e, errorHeaders);
	}

	@ExceptionHandler(GameException.class)
	@ResponseStatus(INTERNAL_SERVER_ERROR)
	@ResponseBody
	Exception handleException(Exception e) {
		return e;
	}
}
