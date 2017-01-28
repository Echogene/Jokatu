package jokatu.components.controllers.game;

import jokatu.components.config.FactoryConfiguration.GameFactories;
import jokatu.components.config.InputDeserialisers;
import jokatu.components.dao.GameDao;
import jokatu.components.exceptions.StandardExceptionHandler;
import jokatu.components.markup.MarkupGenerator;
import jokatu.game.Game;
import jokatu.game.GameID;
import jokatu.game.event.GameEvent;
import jokatu.game.exception.GameException;
import jokatu.game.input.Input;
import jokatu.game.player.Player;
import jokatu.game.player.PlayerFactory;
import jokatu.game.stage.Stage;
import jokatu.game.viewresolver.ViewResolver;
import ophelia.collections.BaseCollection;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.Map;

import static java.text.MessageFormat.format;
import static ophelia.exceptions.maybe.Maybe.wrap;
import static ophelia.exceptions.maybe.MaybeCollectors.toUniqueSuccess;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * A big controller (that should probably be several) that controls client requests for games.
 * @author Steven Weston
 */
@Controller
public class GameController {

	private final InputDeserialisers inputDeserialisers;
	private final GameFactories gameFactories;
	private final GameDao gameDao;
	private final StandardExceptionHandler standardExceptionHandler;
	private final MarkupGenerator markupGenerator;

	@Autowired
	public GameController(
			InputDeserialisers inputDeserialisers,
			GameFactories gameFactories,
			GameDao gameDao,
			StandardExceptionHandler standardExceptionHandler,
			MarkupGenerator markupGenerator
	) {
		this.inputDeserialisers = inputDeserialisers;
		this.gameFactories = gameFactories;
		this.gameDao = gameDao;
		this.standardExceptionHandler = standardExceptionHandler;
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
		modelAndView.addObject("player", player);
		modelAndView.addObject("otherPlayers", game.getOtherPlayers(player.getName()));
		modelAndView.addObject("gameId", identity);
		modelAndView.addObject("gameName", game.getGameName());
		modelAndView.addObject("gameNames", gameFactories.getGameNames());
		return modelAndView;
	}

	@MessageMapping("/topic/input.game.{identity}")
	public void input(@DestinationVariable("identity") GameID identity, @Payload Map<String, Object> json, Principal principal)
			throws GameException {

		Game<? extends Player> game = gameDao.read(identity);
		if (game == null) {
			throw new GameException(
					identity,
					format("Game with ID {0} does not exist.  You can''t input to a game that does not exist.", identity)
			);
		}

		Player player = getPlayer(game, principal.getName());

		Stage<? extends GameEvent> currentStage = game.getCurrentStage();
		if (currentStage == null) {
			throw new GameException(identity, "The game hasn't started yet.");
		}
		BaseCollection<Class<? extends Input>> acceptedInputs = currentStage.getAcceptedInputs();
		Input input;
		if (acceptedInputs.isEmpty()) {
			input = new Input() {};
		} else {
			input = acceptedInputs.stream()
					.map(inputDeserialisers::getDeserialiser)
					.map(wrap(deserialiser -> deserialiser.deserialise(json)))
					.collect(toUniqueSuccess())
					.returnOnSuccess()
					.throwMappedFailure(e -> new GameException(identity, e, "Could not deserialise ''{0}''.", json));
		}
		game.accept(input, player);
	}

	@NotNull
	private Player getPlayer(@NotNull Game<?> game, String name) {
		Player player = game.getPlayerByName(name);
		if (player == null) {
			PlayerFactory<?> factory = gameFactories.getPlayerFactory(game);
			return factory.produce(game, name);
		} else {
			return player;
		}
	}

	@MessageExceptionHandler(Exception.class)
	void handleException(Exception e, Message originalMessage, Principal principal) {
		standardExceptionHandler.handleMessageException(e, originalMessage, principal);
	}

	@ExceptionHandler(GameException.class)
	@ResponseStatus(INTERNAL_SERVER_ERROR)
	@ResponseBody
	Exception handleException(Exception e) {
		return e;
	}
}
