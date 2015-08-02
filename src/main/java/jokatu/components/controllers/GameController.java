package jokatu.components.controllers;

import jokatu.components.config.FactoryConfiguration.GameFactories;
import jokatu.components.dao.GameDao;
import jokatu.game.Game;
import jokatu.game.GameID;
import jokatu.game.factory.GameFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author Steven Weston
 */
@Controller
public class GameController {

	private final GameFactories gameFactories;

	private final GameDao gameDao;

	@Autowired
	public GameController(GameFactories gameFactories, GameDao gameDao) {
		this.gameFactories = gameFactories;
		this.gameDao = gameDao;
	}

	@RequestMapping("/games")
	ModelAndView games() {
		// Pro tip: Never give the view the same name as an attribute in the model if you want to use that attribute.
		SortedSet<Game<?, ?, ?, ?>> games = new TreeSet<>((g, h) -> g.getIdentifier().compareTo(h.getIdentifier()));
		games.addAll(gameDao.getAll().getUnmodifiableInnerSet());
		return new ModelAndView("views/game_list", "games", games);
	}

	@RequestMapping("/game/{identity}")
	ModelAndView game(GameID identifier) {
		return new ModelAndView("views/game_view", "game", gameDao.read(identifier));
	}

	@SubscribeMapping("/game/{identity}")
	Game<?, ?, ?, ?> gameSocket(@DestinationVariable("identity") GameID identifier) {
		return gameDao.read(identifier);
	}

	@RequestMapping(value = "/createGame.do", method = POST)
	@ResponseBody
	Game<?, ?, ?, ?> createGame(@RequestParam("gameName") String gameName) {

		GameFactory factory = gameFactories.getFactory(gameName);
		Game game = factory.produce();
		gameDao.register(game);

		return game;
	}

	@RequestMapping(value = "/joinGame.do", method = POST)
	@ResponseBody
	Game<?, ?, ?, ?> join(@RequestParam("gameID") GameID identifier, Principal principal) {
		Game<?, ?, ?, ?> game = gameDao.read(identifier);
		// todo: create a player and join
		return game;
	}
}
