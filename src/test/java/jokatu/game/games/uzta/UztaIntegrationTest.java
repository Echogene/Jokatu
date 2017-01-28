package jokatu.game.games.uzta;

import jokatu.components.controllers.game.GameController;
import jokatu.components.dao.GameDao;
import jokatu.game.Game;
import jokatu.game.GameID;
import jokatu.game.exception.GameException;
import jokatu.game.games.uzta.game.Uzta;
import jokatu.game.player.Player;
import jokatu.test.JokatuTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static jokatu.game.games.uzta.game.Uzta.UZTA;
import static ophelia.util.MapUtils.createMap;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@JokatuTest
public class UztaIntegrationTest {

	@Autowired
	private GameDao gameDao;

	@Autowired
	private GameController gameController;

	@Test
	public void should_be_able_to_create_Uzta() throws Exception {
		createUzta();
	}

	private Uzta createUzta() throws GameException {
		long originalCount = gameDao.count();
		gameController.input(
				new GameID(0),
				createMap("gameName", UZTA),
				new UsernamePasswordAuthenticationToken("user", "lol")
		);
		assertThat(gameDao.count(), is(originalCount + 1));

		Game<? extends Player> newGame = gameDao.read(new GameID(originalCount));
		assertThat(newGame, instanceOf(Uzta.class));

		return (Uzta) newGame;
	}
}
