package jokatu.game.games.uzta.game;

import jokatu.game.GameID;
import jokatu.game.event.GameEvent;
import jokatu.game.event.StageOverEvent;
import jokatu.game.exception.GameException;
import jokatu.game.games.uzta.event.GraphUpdatedEvent;
import jokatu.game.games.uzta.graph.LineSegment;
import jokatu.game.games.uzta.graph.ModifiableUztaGraph;
import jokatu.game.games.uzta.graph.Node;
import jokatu.game.games.uzta.graph.NodeType;
import jokatu.game.games.uzta.input.RandomiseGraphInput;
import jokatu.game.games.uzta.input.SelectEdgeInput;
import jokatu.game.games.uzta.player.UztaPlayer;
import jokatu.game.input.AwaitingInputEvent;
import jokatu.game.input.finishstage.EndStageInput;
import jokatu.game.joining.JoinInput;
import jokatu.game.joining.PlayerJoinedEvent;
import jokatu.game.turn.TurnChangedEvent;
import ophelia.collections.bag.BagCollectors;
import ophelia.collections.bag.BaseIntegerBag;
import ophelia.collections.bag.ModifiableIntegerBag;
import ophelia.collections.list.UnmodifiableList;
import ophelia.collections.set.HashSet;
import ophelia.exceptions.voidmaybe.VoidMaybe;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
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

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private Uzta game;
	private UztaPlayer player;
	private UztaPlayer player2;
	private UztaPlayer player3;

	@Before
	public void setUp() throws Exception {
		game = new Uzta(new GameID(0));
		// Start the game.
		game.advanceStage();

		player = new UztaPlayer("Player");
		player2 = new UztaPlayer("Player 2");
		player3 = new UztaPlayer("Player 3");
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

		List<GameEvent> events = captureEvents();

		selectEdge(player, edge);

		assertThat(events, hasItem(instanceOf(GraphUpdatedEvent.class)));
		assertThat(events, hasItem(instanceOf(TurnChangedEvent.class)));

		assertThat(edge.getOwner(), is(player));
	}


	@Test
	public void first_placement_stage_should_not_the_same_selection_twice() throws Exception {
		goToFirstPlacement();

		HashSet<LineSegment> edges = game.getGraph().getEdges();
		LineSegment edge = edges.stream().findAny().orElseThrow(() -> new Exception("Cannot find edge"));
		selectEdge(player, edge);

		expectedException.expectMessage("You already own that edge!");
		selectEdge(player, edge);
	}

	@Test
	public void first_placement_stage_should_accept_two_edge_selections_and_end_stage() throws Exception {
		goToFirstPlacement();

		HashSet<LineSegment> edges = game.getGraph().getEdges();

		List<LineSegment> twoEdges = edges.stream()
				.limit(2)
				.collect(toList());

		List<GameEvent> events = captureEvents();

		twoEdges.stream()
				.map(edge -> new SelectEdgeInput(edge.getFirst().getId(), edge.getSecond().getId()))
				.map(VoidMaybe.wrapOutput(input -> game.accept(input, player)))
				.forEach(result -> result.throwMappedFailure(RuntimeException::new));

		assertThat(events, hasItem(instanceOf(StageOverEvent.class)));

		twoEdges.stream()
				.map(LineSegment::getOwner)
				.forEach(owner -> assertThat(owner, is(player)));
	}

	@Test
	public void turn_order_should_be_correct_for_two_players() throws Exception {

		goToSetupStageWithTwoPlayers();
		FirstPlacementStage currentStage = (FirstPlacementStage) game.getCurrentStage();
		assert currentStage != null;
		UnmodifiableList<UztaPlayer> playersInOrder = currentStage.getPlayersInOrder();
		UztaPlayer firstPlayer = playersInOrder.get(0);
		UztaPlayer secondPlayer = playersInOrder.get(1);

		HashSet<LineSegment> edges = game.getGraph().getEdges();

		List<LineSegment> fourEdges = edges.stream()
				.limit(4)
				.collect(toList());

		selectEdge(firstPlayer, fourEdges.get(0));
		selectEdge(secondPlayer, fourEdges.get(1));
		selectEdge(secondPlayer, fourEdges.get(2));

		List<GameEvent> events = captureEvents();
		selectEdge(firstPlayer, fourEdges.get(3));
		List<AwaitingInputEvent> awaitingInputEvents = events.stream()
				.filter(AwaitingInputEvent.class::isInstance)
				.map(AwaitingInputEvent.class::cast)
				.collect(Collectors.toList());
		assertThat(awaitingInputEvents, org.hamcrest.collection.IsCollectionWithSize.hasSize(1));
		assertThat(awaitingInputEvents, hasItem(instanceOf(StageOverEvent.class)));
	}

	@Test
	public void turn_order_should_be_correct_for_three_players() throws Exception {

		goToSetupStageWithThreePlayers();
		FirstPlacementStage currentStage = (FirstPlacementStage) game.getCurrentStage();
		assert currentStage != null;
		UnmodifiableList<UztaPlayer> playersInOrder = currentStage.getPlayersInOrder();
		UztaPlayer firstPlayer = playersInOrder.get(0);
		UztaPlayer secondPlayer = playersInOrder.get(1);
		UztaPlayer thirdPlayer = playersInOrder.get(2);

		HashSet<LineSegment> edges = game.getGraph().getEdges();

		List<LineSegment> fourEdges = edges.stream()
				.limit(6)
				.collect(toList());

		selectEdge(firstPlayer, fourEdges.get(0));
		selectEdge(secondPlayer, fourEdges.get(1));
		selectEdge(thirdPlayer, fourEdges.get(2));
		selectEdge(thirdPlayer, fourEdges.get(3));
		selectEdge(secondPlayer, fourEdges.get(4));
		selectEdge(firstPlayer, fourEdges.get(5));
	}

	@Test
	public void main_stage_should_follow_first_placement_stage() throws Exception {
		goToMainStage();
	}

	@Test
	public void player_should_start_with_correct_resources() throws Exception {
		goToMainStage();

		List<LineSegment> edges = game.getGraph().getEdges().stream()
				.filter(edge -> player.equals(edge.getOwner()))
				.collect(Collectors.toList());

		assertThat(edges, org.hamcrest.collection.IsCollectionWithSize.hasSize(2));
		List<Node> ownedNodes = edges.stream()
				.flatMap(LineSegment::stream)
				.collect(Collectors.toList());
		assertThat(ownedNodes, org.hamcrest.collection.IsCollectionWithSize.hasSize(4));
		ModifiableIntegerBag<NodeType> expectedResources = ownedNodes.stream()
				.map(Node::getType)
				.collect(BagCollectors.toBag());

		BaseIntegerBag<NodeType> resourcesLeft = player.getResourcesLeftAfter(expectedResources);
		assertFalse("The player should have at least the resources they start with", resourcesLeft.isLacking());
	}

	private void selectEdge(UztaPlayer firstPlayer, LineSegment edge) throws GameException {
		game.accept(new SelectEdgeInput(edge.getFirst().getId(), edge.getSecond().getId()), firstPlayer);
	}

	private void goToSetupStageWithTwoPlayers() throws GameException {
		game.accept(new JoinInput(), player);
		game.accept(new JoinInput(), player2);
		game.accept(new EndStageInput(), player);
		game.advanceStage();
		assertThat(game.getCurrentStage(), instanceOf(SetupStage.class));

		game.accept(new EndStageInput(), player);
		game.advanceStage();
		assertThat(game.getCurrentStage(), instanceOf(FirstPlacementStage.class));
	}

	private void goToSetupStageWithThreePlayers() throws GameException {
		game.accept(new JoinInput(), player);
		game.accept(new JoinInput(), player2);
		game.accept(new JoinInput(), player3);
		game.accept(new EndStageInput(), player);
		game.advanceStage();
		assertThat(game.getCurrentStage(), instanceOf(SetupStage.class));

		game.accept(new EndStageInput(), player);
		game.advanceStage();
		assertThat(game.getCurrentStage(), instanceOf(FirstPlacementStage.class));
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

	private void goToMainStage() throws GameException {
		goToFirstPlacement();
		List<LineSegment> twoEdges = game.getGraph().getEdges().stream()
				.limit(2)
				.collect(toList());

		twoEdges.stream()
				.map(edge -> new SelectEdgeInput(edge.getFirst().getId(), edge.getSecond().getId()))
				.map(VoidMaybe.wrapOutput(input -> game.accept(input, player)))
				.forEach(result -> result.throwMappedFailure(RuntimeException::new));

		game.advanceStage();
		assertThat(game.getCurrentStage(), instanceOf(MainStage.class));
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