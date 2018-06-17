package jokatu.game.games.uzta.game

import jokatu.game.GameID
import jokatu.game.event.GameEvent
import jokatu.game.event.StageOverEvent
import jokatu.game.event.dialog.DialogRequest
import jokatu.game.exception.GameException
import jokatu.game.games.uzta.event.GraphUpdatedEvent
import jokatu.game.games.uzta.graph.LineSegment
import jokatu.game.games.uzta.graph.ModifiableUztaGraph
import jokatu.game.games.uzta.graph.Node
import jokatu.game.games.uzta.graph.NodeType.CIRCLE
import jokatu.game.games.uzta.input.InitialTradeRequest
import jokatu.game.games.uzta.input.RandomiseGraphInput
import jokatu.game.games.uzta.input.SelectEdgeInput
import jokatu.game.games.uzta.player.UztaPlayer
import jokatu.game.input.AwaitingInputEvent
import jokatu.game.input.endturn.EndTurnInput
import jokatu.game.input.finishstage.EndStageInput
import jokatu.game.joining.JoinInput
import jokatu.game.joining.PlayerJoinedEvent
import jokatu.game.turn.TurnChangedEvent
import junit.framework.TestCase.assertFalse
import ophelia.collections.bag.BagCollectors
import ophelia.exceptions.voidmaybe.VoidMaybe
import ophelia.util.CollectionUtils.consumeInParallel
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.collection.IsCollectionWithSize
import org.hamcrest.collection.IsCollectionWithSize.hasSize
import org.hamcrest.collection.IsEmptyCollection.empty
import org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsCollectionContaining.hasItem
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import java.util.*
import java.util.stream.Collectors
import java.util.stream.Collectors.toList
import kotlin.reflect.full.cast

class UztaTest {

	@Rule @JvmField
	var expectedException = ExpectedException.none()!!

	private lateinit var game: Uzta
	private lateinit var player: UztaPlayer
	private lateinit var player2: UztaPlayer
	private lateinit var player3: UztaPlayer

	@Before
	@Throws(Exception::class)
	fun setUp() {
		game = Uzta(GameID(0))
		// Start the game.
		game.advanceStage()

		player = UztaPlayer("Player")
		player2 = UztaPlayer("Player 2")
		player3 = UztaPlayer("Player 3")
	}

	@Test
	@Throws(Exception::class)
	fun a_player_can_join_the_game() {

		assertThat(game.getPlayers(), hasSize(0))

		val events = captureEvents()

		game.accept(JoinInput(), player)

		assertThat(game.getPlayers(), hasSize(1))
		assertThat(events, hasItem<GameEvent>(instanceOf<Any>(PlayerJoinedEvent::class.java)))

		game.accept(EndStageInput(), player)
		assertThat(events, hasItem<GameEvent>(instanceOf<Any>(StageOverEvent::class.java)))
	}

	@Test
	@Throws(Exception::class)
	fun setup_stage_should_follow_joining_stage() {
		goToSetup()
	}

	@Test
	@Throws(Exception::class)
	fun starting_the_setup_stage_should_randomise_graph() {

		var graph: ModifiableUztaGraph
		graph = game.graph
		assertThat(graph.nodes, `is`(empty()))
		assertThat(graph.edges, `is`(empty()))

		goToSetup()

		graph = game.graph
		assertThat(graph.nodes, `is`(not(empty<Node>())))
		assertThat(graph.edges, `is`(not(empty<LineSegment>())))
	}

	@Test
	@Throws(Exception::class)
	fun randomise_graph_input_should_randomise_graph() {
		goToSetup()

		val oldNodes = ArrayList(game.graph.nodes)
		val oldEdges = ArrayList(game.graph.edges)

		testGraphEquality(oldNodes, oldEdges, true)

		game.accept(RandomiseGraphInput(), player)

		testGraphEquality(oldNodes, oldEdges, false)
	}

	@Test
	@Throws(Exception::class)
	fun ending_setup_stage_should_fire_event() {
		goToSetup()

		val events = captureEvents()

		game.accept(EndStageInput(), player)

		assertThat(events, hasItem<GameEvent>(instanceOf<Any>(StageOverEvent::class.java)))
	}

	@Test
	@Throws(Exception::class)
	fun first_placement_stage_should_follow_setup_stage() {
		goToFirstPlacement()
	}

	@Test
	@Throws(Exception::class)
	fun first_placement_stage_should_accept_edge_selection() {
		goToFirstPlacement()

		val edges = game.graph.edges
		val edge = edges.stream().findAny().orElseThrow { Exception("Cannot find edge") }

		val events = captureEvents()

		selectEdge(player, edge)

		assertThat(events, hasItem<GameEvent>(instanceOf<Any>(GraphUpdatedEvent::class.java)))
		assertThat(events, hasItem<GameEvent>(instanceOf<Any>(TurnChangedEvent::class.java)))

		assertThat<UztaPlayer>(edge.owner, `is`<UztaPlayer>(player))
	}


	@Test
	@Throws(Exception::class)
	fun first_placement_stage_should_not_the_same_selection_twice() {
		goToFirstPlacement()

		val edges = game.graph.edges
		val edge = edges.stream().findAny().orElseThrow { Exception("Cannot find edge") }
		selectEdge(player, edge)

		expectedException.expectMessage("You already own that edge!")
		selectEdge(player, edge)
	}

	@Test
	@Throws(Exception::class)
	fun first_placement_stage_should_accept_two_edge_selections_and_end_stage() {
		goToFirstPlacement()

		val edges = game.graph.edges

		val twoEdges = edges.stream()
				.limit(2)
				.collect(toList())

		val events = captureEvents()

		twoEdges.stream()
				.map { edge -> SelectEdgeInput(edge.first.id, edge.second.id) }
				.map(VoidMaybe.wrap { input -> game.accept(input, player) })
				.forEach { result -> result.throwMappedFailure { RuntimeException(it) } }

		assertThat(events, hasItem<GameEvent>(instanceOf<Any>(StageOverEvent::class.java)))

		twoEdges.stream()
				.map { it.owner }
				.forEach { owner -> assertThat(owner, `is`(player)) }
	}

	@Test
	@Throws(Exception::class)
	fun turn_order_should_be_correct_for_two_players() {

		goToSetupStageWithTwoPlayers()
		val currentStage = (game.currentStage as FirstPlacementStage?)!!
		val playersInOrder = currentStage.playersInOrder
		val firstPlayer = playersInOrder.get(0)
		val secondPlayer = playersInOrder.get(1)

		val edges = game.graph.edges

		val fourEdges = edges.stream()
				.limit(4)
				.collect(toList())

		selectEdge(firstPlayer, fourEdges[0])
		selectEdge(secondPlayer, fourEdges[1])
		selectEdge(secondPlayer, fourEdges[2])

		val events = captureEvents()
		selectEdge(firstPlayer, fourEdges[3])
		val awaitingInputEvents = events.stream()
				.filter { AwaitingInputEvent::class.isInstance(it) }
				.map { AwaitingInputEvent::class.cast(it) }
				.collect(toList())
		assertThat(awaitingInputEvents, IsCollectionWithSize.hasSize(1))
		assertThat(awaitingInputEvents, hasItem<AwaitingInputEvent>(instanceOf<Any>(StageOverEvent::class.java)))
	}

	@Test
	@Throws(Exception::class)
	fun turn_order_should_be_correct_for_three_players() {

		goToMainStageWithThreePlayers()
	}

	@Test
	@Throws(Exception::class)
	fun main_stage_should_follow_first_placement_stage() {
		goToMainStage()
	}

	@Test
	@Throws(Exception::class)
	fun player_should_start_with_correct_resources() {
		goToMainStage()

		val edges = game.graph.edges.stream()
				.filter { edge -> player == edge.owner }
				.collect(Collectors.toList())

		assertThat(edges, IsCollectionWithSize.hasSize(2))
		val ownedNodes = edges.stream()
				.flatMap { it.stream() }
				.collect(toList())
		assertThat(ownedNodes, IsCollectionWithSize.hasSize(4))
		val expectedResources = ownedNodes.stream()
				.map { it.type!! }
				.collect(BagCollectors.toBag())

		val resourcesLeft = player.getResourcesLeftAfter(expectedResources)
		assertFalse("The player should have at least the resources they start with", resourcesLeft.hasLackingItems())
	}

	@Test
	@Throws(GameException::class)
	fun player_should_be_able_to_end_their_turn() {
		goToMainStage()

		val events = captureEvents()

		game.accept(EndTurnInput(), player)

		assertThat(events, hasItem<GameEvent>(instanceOf<Any>(TurnChangedEvent::class.java)))
	}

	@Test
	@Throws(Exception::class)
	fun player_should_be_able_to_request_a_trade() {
		goToMainStageWithThreePlayers()

		val events = captureEvents()

		// Player requests circles from player two.
		game.accept(InitialTradeRequest(player2.name, CIRCLE), player)

		assertThat(events, hasItem<GameEvent>(instanceOf<Any>(DialogRequest::class.java)))
	}

	@Throws(GameException::class)
	private fun selectEdge(firstPlayer: UztaPlayer, edge: LineSegment) {
		game.accept(SelectEdgeInput(edge.first.id, edge.second.id), firstPlayer)
	}

	@Throws(GameException::class)
	private fun goToSetupStageWithTwoPlayers() {
		game.accept(JoinInput(), player)
		game.accept(JoinInput(), player2)
		game.accept(EndStageInput(), player)
		game.advanceStage()
		assertThat(game.currentStage, instanceOf(SetupStage::class.java))

		game.accept(EndStageInput(), player)
		game.advanceStage()
		assertThat(game.currentStage, instanceOf(FirstPlacementStage::class.java))
	}

	@Throws(GameException::class)
	private fun goToFirstPlacementStageWithThreePlayers() {
		game.accept(JoinInput(), player)
		game.accept(JoinInput(), player2)
		game.accept(JoinInput(), player3)
		game.accept(EndStageInput(), player)
		game.advanceStage()
		assertThat(game.currentStage, instanceOf(SetupStage::class.java))

		game.accept(EndStageInput(), player)
		game.advanceStage()
		assertThat(game.currentStage, instanceOf(FirstPlacementStage::class.java))
	}

	private fun captureEvents(): List<GameEvent> {
		val events = ArrayList<GameEvent>()
		game.observe { events.add(it) }
		return events
	}

	@Throws(GameException::class)
	private fun goToSetup() {
		game.accept(JoinInput(), player)
		game.accept(EndStageInput(), player)
		game.advanceStage()
		assertThat(game.currentStage, instanceOf(SetupStage::class.java))
	}

	@Throws(GameException::class)
	private fun goToFirstPlacement() {
		goToSetup()
		game.accept(EndStageInput(), player)
		game.advanceStage()
		assertThat(game.currentStage, instanceOf(FirstPlacementStage::class.java))
	}

	@Throws(GameException::class)
	private fun goToMainStage() {
		goToFirstPlacement()
		val twoEdges = game.graph.edges.stream()
				.limit(2)
				.collect(toList())

		twoEdges.stream()
				.map { edge -> SelectEdgeInput(edge.first.id, edge.second.id) }
				.map(VoidMaybe.wrap { input -> game.accept(input, player) })
				.forEach { result -> result.throwMappedFailure { RuntimeException(it) } }

		game.advanceStage()
		assertThat(game.currentStage, instanceOf(MainStage::class.java))
	}

	@Throws(GameException::class)
	private fun goToMainStageWithThreePlayers() {
		goToFirstPlacementStageWithThreePlayers()
		val currentStage = (game.currentStage as FirstPlacementStage?)!!
		val playersInOrder = currentStage.playersInOrder
		val firstPlayer = playersInOrder.get(0)
		val secondPlayer = playersInOrder.get(1)
		val thirdPlayer = playersInOrder.get(2)

		val edges = game.graph.edges

		val sixEdges = edges.stream()
				.limit(6)
				.collect(toList())

		selectEdge(firstPlayer, sixEdges[0])
		selectEdge(secondPlayer, sixEdges[1])
		selectEdge(thirdPlayer, sixEdges[2])
		selectEdge(thirdPlayer, sixEdges[3])
		selectEdge(secondPlayer, sixEdges[4])
		selectEdge(firstPlayer, sixEdges[5])

		game.advanceStage()
		assertThat(game.currentStage, instanceOf(MainStage::class.java))
	}

	private fun testGraphEquality(oldNodes: List<Node>, oldEdges: List<LineSegment>, equality: Boolean) {
		val newNodes = ArrayList(game.graph.nodes)
		consumeInParallel(
				oldNodes,
				newNodes
		) { oldNode, newNode -> testNodeEquality(oldNode, newNode, equality) }
		val newEdges = ArrayList(game.graph.edges)
		if (equality) {
			assertThat<List<LineSegment>>("The edges were not equal", newEdges, containsInAnyOrder<Any>(*oldEdges.toTypedArray()))
		} else {
			assertThat<List<LineSegment>>(
					"Either the graph has not been randomised or you're extremely unlucky that the randomisations happened to create the same edge sets twice",
					newEdges,
					not(containsInAnyOrder<Any>(*oldEdges.toTypedArray()))
			)
		}
	}

	private fun testNodeEquality(oldNode: Node, newNode: Node, equality: Boolean) {
		val nodesEqual = oldNode.x == newNode.x && oldNode.y == newNode.y
		if (equality) {
			assertTrue("The nodes were not equal", nodesEqual)
		} else {
			assertFalse(
					"Either the graph has not been randomised or you're extremely unlucky that two ordered pairs of random doubles coïncided",
					nodesEqual
			)
		}
	}
}