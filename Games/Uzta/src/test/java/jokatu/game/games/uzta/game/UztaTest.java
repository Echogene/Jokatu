package jokatu.game.games.uzta.game;

import jokatu.game.GameID;
import jokatu.game.event.GameEvent;
import jokatu.game.event.StageOverEvent;
import jokatu.game.games.uzta.graph.LineSegment;
import jokatu.game.games.uzta.graph.ModifiableUztaGraph;
import jokatu.game.games.uzta.graph.Node;
import jokatu.game.games.uzta.input.RandomiseGraphInput;
import jokatu.game.games.uzta.player.UztaPlayer;
import jokatu.game.joining.JoinInput;
import jokatu.game.joining.PlayerJoinedEvent;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static ophelia.collections.matchers.IsCollectionWithSize.hasSize;
import static ophelia.collections.matchers.IsEmptyCollection.empty;
import static ophelia.util.CollectionUtils.consumeInParallel;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertTrue;

public class UztaTest {

	private Uzta game;
	private UztaPlayer player;

	@Before
	public void setUp() throws Exception {
		game = new Uzta(new GameID(0));
		// Start the game.
		game.advanceStage();

		player = new UztaPlayer("Player");
	}

	@Test
	public void a_player_can_join_the_game() throws Exception {

		assertThat(game.getPlayers(), hasSize(0));

		List<GameEvent> events = new ArrayList<>();
		game.observe(events::add);

		game.accept(new JoinInput(), player);

		assertThat(game.getPlayers(), hasSize(1));
		assertThat(events, hasItem(instanceOf(PlayerJoinedEvent.class)));
		assertThat(events, hasItem(instanceOf(StageOverEvent.class)));
	}

	@Test
	public void setup_stage_should_follow_joining_stage() throws Exception {

		game.accept(new JoinInput(), player);
		game.advanceStage();

		assertThat(game.getCurrentStage(), instanceOf(SetupStage.class));
	}

	@Test
	public void starting_the_setup_stage_should_randomise_graph() throws Exception {

		ModifiableUztaGraph graph;
		graph = game.getGraph();
		assertThat(graph.getNodes(), is(empty()));
		assertThat(graph.getEdges(), is(empty()));

		game.accept(new JoinInput(), player);
		game.advanceStage();

		assertThat(game.getCurrentStage(), instanceOf(SetupStage.class));

		graph = game.getGraph();
		assertThat(graph.getNodes(), is(not(empty())));
		assertThat(graph.getEdges(), is(not(empty())));
	}

	@Test
	public void randomise_graph_input_should_randomise_graph() throws Exception {

		game.accept(new JoinInput(), player);
		game.advanceStage();
		assertThat(game.getCurrentStage(), instanceOf(SetupStage.class));

		List<Node> oldNodes = new ArrayList<>(game.getGraph().getNodes());
		List<LineSegment> oldEdges = new ArrayList<>(game.getGraph().getEdges());

		testGraphEquality(oldNodes, oldEdges, true);

		game.accept(new RandomiseGraphInput(), player);

		testGraphEquality(oldNodes, oldEdges, false);
	}

	private void testGraphEquality(List<Node> oldNodes, List<LineSegment> oldEdges, boolean equality) {
		List<Node> newNodes = new ArrayList<>(game.getGraph().getNodes());
		consumeInParallel(
				oldNodes,
				newNodes,
				(oldNode, newNode) -> testNodeEquality(oldNode, newNode, equality)
		);
		List<LineSegment> newEdges = new ArrayList<>(game.getGraph().getEdges());
		if (equality) {
			assertThat("The edges were not equal", newEdges, containsInAnyOrder(oldEdges.toArray()));
		} else {
			assertThat(
					"Either the graph has not been randomised or you're extremely unlucky that the randomisations happened to create the same edge sets twice",
					newEdges,
					not(containsInAnyOrder(oldEdges.toArray()))
			);
		}
	}

	private void testNodeEquality(Node oldNode, Node newNode, boolean equality) {
		boolean nodesEqual = oldNode.getX() == newNode.getX() && oldNode.getY() == newNode.getY();
		if (equality) {
			assertTrue("The nodes were not equal", nodesEqual);
		} else {
			assertFalse(
					"Either the graph has not been randomised or you're extremely unlucky that two ordered pairs of random doubles coïncided",
					nodesEqual
			);
		}
	}
}