package jokatu.components.controllers.game;

import jokatu.components.config.FactoryConfiguration.GameFactories;
import jokatu.components.dao.GameDao;
import jokatu.components.markup.MarkupGenerator;
import jokatu.components.stomp.StoringMessageSender;
import jokatu.game.Game;
import jokatu.game.GameFactory;
import jokatu.game.GameID;
import jokatu.game.event.EventHandler;
import jokatu.game.event.GameEvent;
import jokatu.game.exception.GameException;
import jokatu.game.input.Input;
import jokatu.game.input.InputDeserialiser;
import jokatu.game.player.Player;
import jokatu.game.player.PlayerFactory;
import jokatu.game.viewresolver.ViewResolver;
import ophelia.collections.BaseCollection;
import ophelia.exceptions.maybe.FailureHandler;
import ophelia.exceptions.maybe.SuccessHandler;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.*;

import static java.text.MessageFormat.format;
import static ophelia.exceptions.maybe.Maybe.wrapOutput;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * A big controller (that should probably be several) that controls client requests for games.
 * @author Steven Weston
 */
@Controller
public class GameController {

	private static final String GAME_LIST_MAPPING = "/games";

	private final GameFactories gameFactories;
	private final GameDao gameDao;
	private final StoringMessageSender sender;
	private final MarkupGenerator markupGenerator;
	private final Collection<EventHandler> eventHandlers;

	@Autowired
	public GameController(
			GameFactories gameFactories,
			GameDao gameDao,
			StoringMessageSender sender,
			MarkupGenerator markupGenerator,
			Collection<EventHandler> eventHandlers
	) {
		this.gameFactories = gameFactories;
		this.gameDao = gameDao;
		this.sender = sender;
		this.markupGenerator = markupGenerator;
		this.eventHandlers = eventHandlers;
	}

	@RequestMapping(GAME_LIST_MAPPING)
	ModelAndView games() {
		// Pro tip: Never give the view the same name as an attribute in the model if you want to use that attribute.
		Map<String, Object> model = new HashMap<>();

		SortedSet<Game<?>> games = new TreeSet<>((g, h) -> g.getIdentifier().compareTo(h.getIdentifier()));
		games.addAll(gameDao.getAll().getUnmodifiableInnerSet());
		model.put("games", games);

		model.put("gameNames", gameFactories.getGameNames());

		return new ModelAndView("views/game_list", model);
	}

	@RequestMapping("/game/{identity}")
	ModelAndView game(@PathVariable("identity") GameID identity, Principal principal) throws GameException {
		Game<? extends Player> game = gameDao.read(identity);
		if (game == null) {
			return new ModelAndView(new RedirectView(GAME_LIST_MAPPING));
		}
		ViewResolver<?, ?> viewResolver = gameFactories.getViewResolver(game);
		Player player = getPlayer(principal, game);
		ModelAndView modelAndView;
		if (game.hasPlayer(player)) {
			modelAndView = viewResolver.getViewForPlayer(player);
		} else {
			modelAndView = viewResolver.getViewForObserver();
		}
		modelAndView.addObject("markupGenerator", markupGenerator);
		modelAndView.addObject("username", principal.getName());
		return modelAndView;
	}

	/**
	 * Users can subscribe to public events for a game without being a player.  Examples of public events would include:
	 * <ul>
	 *     <li>Players joining the game</li>
	 *     <li>Players leaving the game</li>
	 *     <li>Results of dice rolls</li>`
	 *     <li>The results of the end of the game</li>
	 * </ul>
	 */
	@SubscribeMapping("/topic/public.game.{identity}")
	void publicGameSubscription(
			@DestinationVariable("identity") GameID identity
	) throws GameException {
		getGame(identity, "You cannot subscribe to a non-existent game.");
	}

	@SubscribeMapping("/topic/status.game.{identity}")
	void gameStatusSubscription(
			@DestinationVariable("identity") GameID identity
	) throws GameException {
		getGame(identity, "You cannot subscribe to a non-existent game.");
	}

	@SubscribeMapping("/topic/substatus.game.{identity}.*")
	void gameSubstatusSubscription(
			@DestinationVariable("identity") GameID identity
	) throws GameException {
		getGame(identity, "You cannot subscribe to a non-existent game.");
	}

	@NotNull
	private Game<? extends Player> getGame(
			GameID identity,
			@NotNull final String errorMessage
	) throws GameException {

		Game<? extends Player> game = gameDao.read(identity);
		if (game == null) {
			throw new GameException(
					identity,
					format("Game with ID {0} does not exist.  {1}", identity, errorMessage)
			);
		}
		return game;
	}

	@MessageMapping("/topic/input.game.{identity}")
	void input(@DestinationVariable("identity") GameID identity, @Payload Map<String, Object> json, Principal principal)
			throws GameException {

		Game<? extends Player> game = getGame(identity, "You can't input to a game that does not exist.");

		Player player = getPlayer(principal, game);
		BaseCollection<? extends InputDeserialiser> deserialisers = gameFactories.getInputDeserialisers(game);
		Input input = deserialisers.stream()
				.map(wrapOutput(deserialiser -> deserialiser.deserialise(json)))
				.map(SuccessHandler::returnOnSuccess)
				.map(FailureHandler::nullOnFailure)
				.filter(i -> i != null)
				.findAny()
				.orElseThrow(() -> new GameException(identity, "Could not deserialise ''{0}''", json));
		game.accept(input, player);
	}

	@RequestMapping(value = "/createGame.do", method = POST)
	@ResponseBody
	Game createGame(@RequestParam("gameName") String gameName, Principal principal) {

		GameFactory factory = gameFactories.getFactory(gameName);
		Game<?> game = factory.produceGame(principal.getName());

		game.observe(event -> sendEvent(game, event));
		return game;
	}

	/**
	 * Users are allowed to subscribe to a private channel for a game, but unless they're players, they probably won't
	 * receive any messages for it.
	 */
	@SubscribeMapping("/user/topic/private.game.{identity}")
	void privateSubscription(
			@DestinationVariable("identity") GameID identity,
			Principal principal
	) throws GameException {
		getGame(identity, "You cannot subscribe to a non-existent game.");
	}

	private void sendEvent(@NotNull Game game, @NotNull GameEvent event) {
		eventHandlers.forEach(eventHandler -> eventHandler.handle(game, event));
	}

	@NotNull
	private Player getPlayer(@NotNull Principal principal, @NotNull Game<? extends Player> game) {
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
				sender.sendToUser(principal.getName(), "/errors.game." + e.getId(), e);
		}
	}

	private void handleSubscriptionError(GameException e, StompHeaderAccessor accessor, Principal principal) {
		String subscriptionId = accessor.getNativeHeader("id").get(0);

		Map<String, Object> errorHeaders = new HashMap<>();
		errorHeaders.put("subscription-id", subscriptionId);

		sender.sendToUser(principal.getName(), "/errors.game." + e.getId(), e, errorHeaders);
	}

	@ExceptionHandler(GameException.class)
	@ResponseStatus(INTERNAL_SERVER_ERROR)
	@ResponseBody
	Exception handleException(Exception e) {
		return e;
	}
}
