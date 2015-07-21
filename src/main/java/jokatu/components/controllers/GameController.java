package jokatu.components.controllers;

import jokatu.components.dao.GameDao;
import jokatu.game.EmptyGame;
import jokatu.game.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.concurrent.atomic.AtomicLong;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author Steven Weston
 */
@Controller
public class GameController {

	private final AtomicLong atomicLong = new AtomicLong(0);

	private final GameDao gameDao;

	@Autowired
	public GameController(GameDao gameDao) {
		this.gameDao = gameDao;

		createGame();
	}

	@RequestMapping("/games")
	ModelAndView games() {
		// Pro tip: Never give the view the same name as an attribute in the model if you want to use that attribute.
		return new ModelAndView("views/game_list", "games", gameDao.getAll().getUnmodifiableInnerSet());
	}

	@RequestMapping(value = "/createGame.do", method = GET)
	@ResponseBody
	Game<?, ?> createGame() {

		// todo: obviously move this to a factory or something
		EmptyGame game = new EmptyGame(atomicLong.getAndIncrement());

		gameDao.register(game);

		return game;
	}
}
