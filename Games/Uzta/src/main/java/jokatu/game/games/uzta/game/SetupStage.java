package jokatu.game.games.uzta.game;

import jokatu.game.event.GameEvent;
import jokatu.game.games.uzta.event.GraphUpdatedEvent;
import jokatu.game.games.uzta.graph.LineSegment;
import jokatu.game.games.uzta.graph.Node;
import jokatu.game.games.uzta.graph.NodeType;
import jokatu.game.games.uzta.graph.Trigon;
import jokatu.game.games.uzta.input.RandomiseGraphInput;
import jokatu.game.player.StandardPlayer;
import jokatu.game.stage.SingleInputStage;
import ophelia.collections.BaseCollection;
import ophelia.collections.set.HashSet;
import ophelia.collections.set.UnmodifiableSet;
import ophelia.graph.BiGraph;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;

/**
 * The stage of {@link Uzta} where the game is set up.
 */
public class SetupStage extends SingleInputStage<RandomiseGraphInput, StandardPlayer, GameEvent> {

	private final List<Node> nodes = new ArrayList<>();
	private final Set<LineSegment> edges = new HashSet<>();

	private final BiGraph<Node, LineSegment> graph = new BiGraph<Node, LineSegment>() {
		@Override
		public BaseCollection<LineSegment> getEdges() {
			return new UnmodifiableSet<>(edges);
		}

		@Override
		public BaseCollection<Node> getNodes() {
			return new UnmodifiableSet<>(nodes);
		}
	};

	private void randomiseGraph() {
		nodes.clear();
		edges.clear();
		Random random = new Random();
		createNodesInRandomPositions(random);
		delaunayTrigonate();
		randomiseNodeTypes(random);
		fireEvent(new GraphUpdatedEvent(graph));
	}

	private void createNodesInRandomPositions(Random random) {
		for (int i = 0; i < 50; i++) {
			for (int tries = 0; tries < 100; tries++) {
				Node node = new Node("node_" + i, random.nextDouble() * 100, random.nextDouble() * 100);
				if (isFarEnoughAnotherNode(node)) {
					nodes.add(node);
					break;
				}
			}
		}
	}

	private void delaunayTrigonate() {
		Set<Trigon> trigonation = new HashSet<>();

		Trigon superTrigon = new Trigon(
				new Node("0",   0,   0),
				new Node("x", 200,   0),
				new Node("y",   0, 200)
		);

		trigonation.add(superTrigon);

		for (Node node : nodes) {
			Set<Trigon> badTrigons = trigonation.stream()
					.filter(trigon -> trigon.circumcircleContains(node))
					.collect(toSet());

			badTrigons.stream()
					.peek(trigonation::remove)
					.map(Trigon::getEdges)
					.flatMap(Collection::stream)
					.filter(edge -> badTrigons.stream().filter(Δ -> Δ.getEdges().contains(edge)).count() == 1)
					.map(edge -> new Trigon(edge, node))
					.forEach(trigonation::add);
		}

		trigonation.stream()
				.filter(Δ -> Δ.getNodes().stream().noneMatch(superTrigon.getNodes()::contains))
				.map(Trigon::getEdges)
				.flatMap(Collection::stream)
				.forEach(edges::add);
	}

	private void randomiseNodeTypes(Random random) {

		List<Node> nodesToProcess = new ArrayList<>(nodes);

		int numberOfNodes = nodesToProcess.size();
		int numberOfTypes = NodeType.values().length;
		double averageNumberOfEachType = (double) numberOfNodes / (double) numberOfTypes;

		// Partition (most of) the nodes into the types to ensure that there are nodes of all types.
		double targetNumberOfEachType = averageNumberOfEachType - random.nextDouble() * (double) numberOfNodes / 10.0;
		stream(NodeType.values()).forEach(type -> {
			for (int i = 0; i < (int) targetNumberOfEachType; i++) {
				Node node = nodesToProcess.remove(random.nextInt(nodesToProcess.size()));
				node.setType(type);
			}
		});

		// Set the types of the remaining nodes to random ones.  Obviously, this will introduce bias towards certain
		// types.
		nodesToProcess.forEach(node -> node.setType(NodeType.values()[random.nextInt(numberOfTypes)]));
	}

	private boolean isFarEnoughAnotherNode(Node node) {
		return nodes.stream()
				.allMatch(existingNode -> existingNode.distanceFrom(node) > 10);
	}

	@Override
	public void start() {
		randomiseGraph();
	}

	@NotNull
	@Override
	protected Class<RandomiseGraphInput> getInputClass() {
		return RandomiseGraphInput.class;
	}

	@NotNull
	@Override
	protected Class<StandardPlayer> getPlayerClass() {
		return StandardPlayer.class;
	}

	@Override
	protected void acceptCastInputAndPlayer(@NotNull RandomiseGraphInput input, @NotNull StandardPlayer inputter) throws Exception {
		randomiseGraph();
	}
}
