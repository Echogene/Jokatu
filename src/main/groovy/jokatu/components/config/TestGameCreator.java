package jokatu.components.config;

import jokatu.components.dao.GameDao;
import jokatu.components.eventhandlers.GameCreatedEventHandler;
import jokatu.game.GameID;
import jokatu.game.exception.GameException;
import jokatu.game.games.gameofgames.event.GameCreatedEvent;
import jokatu.game.games.gameofgames.game.GameOfGames;
import jokatu.game.games.uzta.game.FirstPlacementStage;
import jokatu.game.games.uzta.game.Uzta;
import jokatu.game.games.uzta.graph.LineSegment;
import jokatu.game.games.uzta.input.SelectEdgeInput;
import jokatu.game.games.uzta.player.UztaPlayer;
import jokatu.game.input.finishstage.EndStageInput;
import jokatu.game.joining.JoinInput;
import jokatu.game.player.StandardPlayer;
import ophelia.collections.list.UnmodifiableList;
import ophelia.collections.set.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Configuration
public class TestGameCreator {

	private final GameCreatedEventHandler gameCreatedEventHandler;
	private final GameOfGames theGameOfGames;
	private final GameDao gameDao;

	@Autowired
	public TestGameCreator(InitialGameOfGamesConfiguration configuration, GameCreatedEventHandler gameCreatedEventHandler, GameDao gameDao) {
		theGameOfGames = configuration.getTheGameOfGames();
		this.gameCreatedEventHandler = gameCreatedEventHandler;
		this.gameDao = gameDao;
	}

	@PostConstruct
	public void createTestGames() throws GameException {
		createGame(Uzta.UZTA);

		Uzta game = (Uzta) gameDao.read(new GameID(1));
		assert game != null;

		UztaPlayer user = new UztaPlayer("user");
		UztaPlayer user2 = new UztaPlayer("user2");
		UztaPlayer user3 = new UztaPlayer("user3");
		game.accept(new JoinInput(), user);
		game.accept(new JoinInput(), user2);
		game.accept(new JoinInput(), user3);

		game.accept(new EndStageInput(), user);

		game.accept(new EndStageInput(), user);

		FirstPlacementStage currentStage = (FirstPlacementStage) game.getCurrentStage();
		UnmodifiableList<UztaPlayer> playersInOrder = currentStage.getPlayersInOrder();
		UztaPlayer firstPlayer = playersInOrder.get(0);
		UztaPlayer secondPlayer = playersInOrder.get(1);
		UztaPlayer thirdPlayer = playersInOrder.get(2);

		HashSet<LineSegment> edges = game.getGraph().getEdges();

		List<LineSegment> fourEdges = edges.stream()
				.limit(6)
				.collect(toList());

		selectEdge(game, firstPlayer, fourEdges.get(0));
		selectEdge(game, secondPlayer, fourEdges.get(1));
		selectEdge(game, thirdPlayer, fourEdges.get(2));
		selectEdge(game, thirdPlayer, fourEdges.get(3));
		selectEdge(game, secondPlayer, fourEdges.get(4));
		selectEdge(game, firstPlayer, fourEdges.get(5));
	}

	private void selectEdge(Uzta game, UztaPlayer firstPlayer, LineSegment edge) throws GameException {
		game.accept(new SelectEdgeInput(edge.getFirst().getId(), edge.getSecond().getId()), firstPlayer);
	}

	private void createGame(String gameName) {
		gameCreatedEventHandler.handle(theGameOfGames, new GameCreatedEvent(new StandardPlayer(""), gameName));
	}
}
