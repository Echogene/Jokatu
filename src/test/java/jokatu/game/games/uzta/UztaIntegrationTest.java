package jokatu.game.games.uzta;

import jokatu.components.controllers.game.GameController;
import jokatu.components.dao.GameDao;
import jokatu.components.ui.DialogController;
import jokatu.components.ui.DialogManager;
import jokatu.components.ui.DialogManager.DialogUI;
import jokatu.game.Game;
import jokatu.game.GameID;
import jokatu.game.exception.GameException;
import jokatu.game.games.uzta.game.FirstPlacementStage;
import jokatu.game.games.uzta.game.MainStage;
import jokatu.game.games.uzta.game.SetupStage;
import jokatu.game.games.uzta.game.Uzta;
import jokatu.game.games.uzta.graph.LineSegment;
import jokatu.game.games.uzta.graph.NodeType;
import jokatu.game.games.uzta.player.UztaPlayer;
import jokatu.game.player.Player;
import jokatu.test.JokatuTest;
import ophelia.collections.list.UnmodifiableList;
import ophelia.collections.set.HashSet;
import ophelia.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static jokatu.components.ui.DialogResponder.DIALOG_ID;
import static jokatu.game.games.uzta.game.Uzta.UZTA;
import static ophelia.collections.matchers.IsCollectionWithSize.hasSize;
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

	@Autowired
	private DialogManager dialogManager;

	@Autowired
	private DialogController dialogController;

	@Test
	public void should_be_able_to_create_Uzta() throws Exception {
		createUzta();
	}

	private Uzta createUzta() throws GameException {
		long originalCount = gameDao.count();
		gameController.input(
				new GameID(0),
				createMap("gameName", UZTA),
				getPrincipal("user")
		);
		assertThat(gameDao.count(), is(originalCount + 1));

		Game<? extends Player> newGame = gameDao.read(new GameID(originalCount));
		assertThat(newGame, instanceOf(Uzta.class));

		return (Uzta) newGame;
	}

	@NotNull
	private UsernamePasswordAuthenticationToken getPrincipal(String name) {
		return new UsernamePasswordAuthenticationToken(name, "lol");
	}

	@Test
	public void players_should_be_able_to_join() throws Exception {
		Uzta uzta = setUpGameWithThreePlayers();

		assertThat(uzta.getPlayers(), hasSize(3));
	}

	private Uzta setUpGameWithThreePlayers() throws GameException {
		Uzta uzta = createUzta();

		gameController.input(
				uzta.getIdentifier(),
				createMap("join", true),
				getPrincipal("user")
		);
		gameController.input(
				uzta.getIdentifier(),
				createMap("join", true),
				getPrincipal("user2")
		);
		gameController.input(
				uzta.getIdentifier(),
				createMap("join", true),
				getPrincipal("user3")
		);
		return uzta;
	}

	@Test
	public void should_be_able_to_get_to_setup_stage() throws Exception {
		Uzta uzta = setUpSetupStageWithThreePlayers();

		assertThat(uzta.getCurrentStage(), instanceOf(SetupStage.class));
	}

	private Uzta setUpSetupStageWithThreePlayers() throws GameException {
		Uzta uzta = setUpGameWithThreePlayers();

		gameController.input(
				uzta.getIdentifier(),
				createMap("end", true),
				getPrincipal("user")
		);
		return uzta;
	}

	@Test
	public void should_be_able_to_get_to_first_placement_stage() throws Exception {
		Uzta uzta = setUpFirstPlacementStageWithThreePlayers();

		assertThat(uzta.getCurrentStage(), instanceOf(FirstPlacementStage.class));
	}

	private Uzta setUpFirstPlacementStageWithThreePlayers() throws GameException {
		Uzta uzta = setUpSetupStageWithThreePlayers();

		gameController.input(
				uzta.getIdentifier(),
				createMap("end", true),
				getPrincipal("user")
		);
		return uzta;
	}

	@Test
	public void should_be_able_to_get_to_main_stage() throws Exception {
		Uzta uzta = setUpMainStageWithThreePlayers();

		assertThat(uzta.getCurrentStage(), instanceOf(MainStage.class));
	}

	private Uzta setUpMainStageWithThreePlayers() throws GameException {
		Uzta uzta = setUpFirstPlacementStageWithThreePlayers();

		FirstPlacementStage currentStage = (FirstPlacementStage) uzta.getCurrentStage();
		assert currentStage != null;
		UnmodifiableList<UztaPlayer> playersInOrder = currentStage.getPlayersInOrder();
		UztaPlayer firstPlayer = playersInOrder.get(0);
		UztaPlayer secondPlayer = playersInOrder.get(1);
		UztaPlayer thirdPlayer = playersInOrder.get(2);

		HashSet<LineSegment> edges = uzta.getGraph().getEdges();

		List<LineSegment> sixEdges = edges.stream()
				.limit(6)
				.collect(toList());

		selectEdge(uzta, firstPlayer, sixEdges.get(0));
		selectEdge(uzta, secondPlayer, sixEdges.get(1));
		selectEdge(uzta, thirdPlayer, sixEdges.get(2));
		selectEdge(uzta, thirdPlayer, sixEdges.get(3));
		selectEdge(uzta, secondPlayer, sixEdges.get(4));
		selectEdge(uzta, firstPlayer, sixEdges.get(5));

		return uzta;
	}

	private void selectEdge(Uzta uzta, UztaPlayer firstPlayer, LineSegment lineSegment) throws GameException {
		gameController.input(
				uzta.getIdentifier(),
				new HashMap<String, Object>() {{
					put("start", lineSegment.getFirst().getId());
					put("end", lineSegment.getSecond().getId());
				}},
				getPrincipal(firstPlayer.getName())
		);
	}

	@SuppressWarnings("OptionalGetWithoutIsPresent")
	@Test
	public void should_be_able_to_trade() throws Exception {
		Uzta uzta = setUpMainStageWithThreePlayers();
		// Look at the current stage for tasty sideeffects.
		assertThat(uzta.getCurrentStage(), instanceOf(MainStage.class));

		UztaPlayer trader = uzta.getPlayers().stream().findAny().get();
		UztaPlayer tradee = uzta.getOtherPlayers(trader.getName()).stream().findAny().get();

		NodeType resourceToTrade = tradee.getResources().stream().map(Pair::getLeft).findAny().get();

		gameController.input(
				uzta.getIdentifier(),
				new HashMap<String, Object>() {{
					put("player", tradee.getName());
					put("resource", resourceToTrade.toString());
				}},
				getPrincipal(trader.getName())
		);

		UnmodifiableList<DialogUI> dialogsForTrader = dialogManager.getDialogsForPlayer(uzta, trader);
		assertThat(dialogsForTrader, hasSize(1));
		DialogUI traderDialog = dialogsForTrader.stream().findAny().get();

		dialogController.input(
				uzta.getIdentifier(),
				new HashMap<String, Object>() {{
					put("player", tradee.getName());
					put(resourceToTrade.toString(), 1);
					put(DIALOG_ID, traderDialog.getDialogId());
				}},
				getPrincipal(trader.getName())
		);
		dialogsForTrader = dialogManager.getDialogsForPlayer(uzta, trader);
		assertThat(dialogsForTrader, hasSize(0));

		UnmodifiableList<DialogUI> dialogsForTradee = dialogManager.getDialogsForPlayer(uzta, tradee);
		assertThat(dialogsForTradee, hasSize(1));
		DialogUI tradeeDialog = dialogsForTradee.stream().findAny().get();
		dialogController.input(
				uzta.getIdentifier(),
				new HashMap<String, Object>() {{
					put("acknowledge", true);
					put(DIALOG_ID, tradeeDialog.getDialogId());
				}},
				getPrincipal(tradee.getName())
		);
	}
}
