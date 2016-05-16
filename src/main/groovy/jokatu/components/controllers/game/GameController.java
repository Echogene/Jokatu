package jokatu.components.controllers.game;

import jokatu.components.config.FactoryConfiguration.GameFactories;
import jokatu.components.dao.GameDao;
import jokatu.components.markup.MarkupGenerator;
import jokatu.components.stomp.StoringMessageSender;
import jokatu.game.Game;
import jokatu.game.GameID;
import jokatu.game.exception.GameException;
import jokatu.game.input.Input;
import jokatu.game.input.InputDeserialiser;
import jokatu.game.player.Player;
import jokatu.game.player.PlayerFactory;
import jokatu.game.viewresolver.ViewResolver;
import jokatu.stomp.SendErrorMessage;
import jokatu.stomp.SubscriptionErrorMessage;
import ophelia.collections.BaseCollection;
import ophelia.exceptions.maybe.FailureHandler;
import ophelia.exceptions.maybe.SuccessHandler;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.text.MessageFormat;
import java.util.Map;

import static java.text.MessageFormat.format;
import static ophelia.exceptions.maybe.Maybe.wrapOutput;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * A big controller (that should probably be several) that controls client requests for games.
 * @author Steven Weston
 */
@Controller
public class GameController {

	private static final Logger log = LoggerFactory.getLogger(GameController.class);

	private final GameFactories gameFactories;
	private final GameDao gameDao;
	private final StoringMessageSender sender;
	private final MarkupGenerator markupGenerator;

	@Autowired
	public GameController(
			GameFactories gameFactories,
			GameDao gameDao,
			StoringMessageSender sender,
			MarkupGenerator markupGenerator
	) {
		this.gameFactories = gameFactories;
		this.gameDao = gameDao;
		this.sender = sender;
		this.markupGenerator = markupGenerator;
	}

	@RequestMapping("/game/{identity}")
	ModelAndView game(@PathVariable("identity") GameID identity, Principal principal) throws GameException {
		Game<? extends Player> game = gameDao.read(identity);
		if (game == null) {
			return new ModelAndView(new RedirectView("/game/0"));
		}
		ViewResolver<?, ?> viewResolver = gameFactories.getViewResolver(game);
		Player player = getPlayer(game, principal.getName());
		ModelAndView modelAndView;
		if (game.hasPlayer(player.getName())) {
			modelAndView = viewResolver.getViewForPlayer(player);
		} else {
			modelAndView = viewResolver.getViewForObserver();
		}
		modelAndView.addObject("markupGenerator", markupGenerator);
		modelAndView.addObject("username", principal.getName());
		modelAndView.addObject("games", gameFactories.getGameNames());
		return modelAndView;
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
	@SubscribeMapping("/topic/public.game.{identity}")
	void publicGameSubscription(
			@DestinationVariable("identity") GameID identity
	) throws GameException {
		getGame(identity, "You cannot subscribe to a non-existent game.");
	}

	@SubscribeMapping("/topic/observers.game.{identity}")
	void usersSubscription(
			@DestinationVariable("identity") GameID identity
	) throws GameException {
		getGame(identity, "You cannot subscribe to a non-existent game.");
	}

	@SubscribeMapping("/topic/players.game.{identity}")
	void playersSubscription(
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

		Player player = getPlayer(game, principal.getName());
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

	@NotNull
	private Player getPlayer(@NotNull Game<? extends Player> game, String name) {
		Player player = game.getPlayerByName(name);
		if (player == null) {
			PlayerFactory factory = gameFactories.getPlayerFactory(game);
			return factory.produce(name);
		} else {
			return player;
		}
	}

	@MessageExceptionHandler(GameException.class)
	void handleException(GameException e, Message originalMessage, Principal principal) {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(originalMessage);
		ErrorMessage errorMessage = getErrorMessage(e, accessor);
		sender.sendMessageToUser(principal.getName(), "/topic/errors.game." + e.getId(), errorMessage);
		log.error(
				MessageFormat.format(
					"Exception occurred when receiving message\n{0}",
					accessor.getDetailedLogMessage(originalMessage.getPayload())
				),
				e
		);
	}

	private ErrorMessage getErrorMessage(GameException e, StompHeaderAccessor accessor) {
		switch (accessor.getCommand()) {
			case SUBSCRIBE:
				String subscribeId = accessor.getNativeHeader("id").get(0);
				return new SubscriptionErrorMessage(e, subscribeId);
			case SEND:
				String sendReceipt = accessor.getNativeHeader("receipt").get(0);
				return new SendErrorMessage(e, sendReceipt);
			default:
				return new ErrorMessage(e);
		}
	}

	@ExceptionHandler(GameException.class)
	@ResponseStatus(INTERNAL_SERVER_ERROR)
	@ResponseBody
	Exception handleException(Exception e) {
		return e;
	}
}
