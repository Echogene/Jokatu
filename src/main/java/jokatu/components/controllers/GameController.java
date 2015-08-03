package jokatu.components.controllers;

import jokatu.components.config.FactoryConfiguration.GameFactories;
import jokatu.components.dao.GameDao;
import jokatu.game.Game;
import jokatu.game.GameID;
import jokatu.game.factory.game.GameFactory;
import jokatu.game.factory.player.PlayerFactory;
import jokatu.game.joining.CannotJoinGameException;
import jokatu.game.user.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
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
		Map<String, Object> model = new HashMap<>();

		SortedSet<Game<?, ?, ?, ?>> games = new TreeSet<>((g, h) -> g.getIdentifier().compareTo(h.getIdentifier()));
		games.addAll(gameDao.getAll().getUnmodifiableInnerSet());
		model.put("games", games);

		model.put("gameNames", gameFactories.getGameNames());

		return new ModelAndView("views/game_list", model);
	}

	@RequestMapping("/game/{identity}")
	ModelAndView game(GameID identifier) {
		Game<?, ?, ?, ?> game = gameDao.read(identifier);
		if (game == null) {
			return new ModelAndView(new RedirectView("/games"));
		}
		return new ModelAndView("views/game_view", "game", game);
	}

	@SubscribeMapping("/game/{identity}")
	Game<?, ?, ?, ?> gameSocket(@DestinationVariable("identity") GameID identifier) {
		return gameDao.read(identifier);
	}

	@RequestMapping(value = "/createGame.do", method = POST)
	@ResponseBody
	Game<?, ?, ?, ?> createGame(@RequestParam("gameName") String gameName) {

		GameFactory factory = gameFactories.getFactory(gameName);
		return factory.produce();
	}

	@RequestMapping(value = "/joinGame.do", method = POST)
	@ResponseBody
	Game<?, ?, ?, ?> join(@RequestParam("gameID") GameID identifier, Principal principal) throws CannotJoinGameException {
		Game<Player, ?, ?, ?> game = gameDao.uncheckedRead(identifier);
		PlayerFactory factory = gameFactories.getPlayerFactory(game);
		Player player = factory.produce(principal.getName());
		game.join(player);
		return game;
	}
}
