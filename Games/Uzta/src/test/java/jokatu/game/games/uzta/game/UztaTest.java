package jokatu.game.games.uzta.game;

import jokatu.game.GameID;
import jokatu.game.event.GameEvent;
import jokatu.game.event.StageOverEvent;
import jokatu.game.exception.GameException;
import jokatu.game.games.uzta.graph.LineSegment;
import jokatu.game.games.uzta.graph.ModifiableUztaGraph;
import jokatu.game.games.uzta.graph.Node;
import jokatu.game.games.uzta.input.RandomiseGraphInput;
import jokatu.game.games.uzta.input.SelectEdgeInput;
import jokatu.game.games.uzta.player.UztaPlayer;
import jokatu.game.input.finishstage.EndStageInput;
import jokatu.game.joining.JoinInput;
import jokatu.game.joining.PlayerJoinedEvent;
import ophelia.collections.set.HashSet;
import org.jetbrains.annotations.NotNull;
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

		List<GameEvent> events = captureEvents();

		game.accept(new JoinInput(), player);

		assertThat(game.getPlayers(), hasSize(1));
		assertThat(events, hasItem(instanceOf(PlayerJoinedEvent.class)));

		game.accept(new EndStageInput(), player);
		assertThat(events, hasItem(instanceOf(StageOverEvent.class)));
	}

	@Test
	public void setup_stage_should_follow_joining_stage() throws Exception {
		goToSetup();
	}

	@Test
	public void starting_the_setup_stage_should_randomise_graph() throws Exception {

		ModifiableUztaGraph graph;
		graph = game.getGraph();
		assertThat(graph.getNodes(), is(empty()));
		assertThat(graph.getEdges(), is(empty()));

		goToSetup();

		graph = game.getGraph();
		assertThat(graph.getNodes(), is(not(empty())));
		assertThat(graph.getEdges(), is(not(empty())));
	}

	@Test
	public void randomise_graph_input_should_randomise_graph() throws Exception {
		goToSetup();

		List<Node> oldNodes = new ArrayList<>(game.getGraph().getNodes());
		List<LineSegment> oldEdges = new ArrayList<>(game.getGraph().getEdges());

		testGraphEquality(oldNodes, oldEdges, true);

		game.accept(new RandomiseGraphInput(), player);

		testGraphEquality(oldNodes, oldEdges, false);
	}

	@Test
	public void ending_setup_stage_should_fire_event() throws Exception {
		goToSetup();

		List<GameEvent> events = captureEvents();

		game.accept(new EndStageInput(), player);

		assertThat(events, hasItem(instanceOf(StageOverEvent.class)));
	}

	@Test
	public void first_placement_stage_should_follow_setup_stage() throws Exception {
		goToFirstPlacement();
	}

	@Test
	public void first_placement_stage_should_accept_edge_selection() throws Exception {
		goToFirstPlacement();

		HashSet<LineSegment> edges = game.getGraph().getEdges();
		LineSegment edge = edges.stream().findAny().orElseThrow(() -> new Exception("Cannot find edge"));
		game.accept(new SelectEdgeInput(edge.getFirst().getId(), edge.getSecond().getId()), player);

		assertThat(edge.getOwner(), is(player));
	}

	@NotNull
	private List<GameEvent> captureEvents() {
		List<GameEvent> events = new ArrayList<>();
		game.observe(events::add);
		return events;
	}

	private void goToSetup() throws GameException {
		game.accept(new JoinInput(), player);
		game.accept(new EndStageInput(), player);
		game.advanceStage();
		assertThat(game.getCurrentStage(), instanceOf(SetupStage.class));
	}

	private void goToFirstPlacement() throws GameException {
		goToSetup();
		game.accept(new EndStageInput(), player);
		game.advanceStage();
		assertThat(game.getCurrentStage(), instanceOf(FirstPlacementStage.class));
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