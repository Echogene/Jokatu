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
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.text.MessageFormat;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	private static final Pattern GAME_CHANNEL_PATTERN = Pattern.compile(".*\\.game\\.(\\d+).*");

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
		modelAndView.addObject("otherPlayersNames", game.getOtherPlayersNames(player.getName()));
		modelAndView.addObject("gameId", identity);
		modelAndView.addObject("gameName", game.getGameName());
		modelAndView.addObject("gameNames", gameFactories.getGameNames());
		return modelAndView;
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

	@NotNull
	private Player getPlayer(@NotNull Game<? extends Player> game, String name) {
		Player player = game.getPlayerByName(name);
		if (player == null) {
			PlayerFactory factory = gameFactories.getPlayerFactory(game);
			return factory.produce(game, name);
		} else {
			return player;
		}
	}

	@MessageExceptionHandler(Exception.class)
	void handleException(Exception e, Message originalMessage, Principal principal) {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(originalMessage);
		String destination = accessor.getDestination();
		Matcher matcher = GAME_CHANNEL_PATTERN.matcher(destination);
		if (matcher.matches()) {
			ErrorMessage errorMessage = getErrorMessage(e, accessor);
			sender.sendMessageToUser(principal.getName(), "/topic/errors.game." + matcher.group(1), errorMessage);
		}
		log.error(
				MessageFormat.format(
					"Exception occurred when receiving message\n{0}",
					accessor.getDetailedLogMessage(originalMessage.getPayload())
				),
				e
		);
	}

	private ErrorMessage getErrorMessage(Throwable e, StompHeaderAccessor accessor) {
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
