package jokatu.game.games.uzta.game;

import jokatu.game.event.GameEvent;
import jokatu.game.games.uzta.event.GraphUpdatedEvent;
import jokatu.game.games.uzta.graph.LineSegment;
import jokatu.game.games.uzta.graph.Node;
import jokatu.game.games.uzta.input.RandomiseGraphInput;
import jokatu.game.player.StandardPlayer;
import jokatu.game.stage.SingleInputStage;
import ophelia.collections.BaseCollection;
import ophelia.collections.set.HashSet;
import ophelia.collections.set.UnmodifiableSet;
import ophelia.graph.BiGraph;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

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
		randomiseNodes(random);
		randomiseEdges(random);
		fireEvent(new GraphUpdatedEvent(graph));
	}

	private void randomiseNodes(Random random) {
		for (int i = 0; i < 20; i++) {
			for (int tries = 0; tries < 100; tries++) {
				Node node = new Node("node_" + i, random.nextDouble() * 100, random.nextDouble() * 100);
				if (isFarEnoughAnotherNode(node)) {
					nodes.add(node);
					break;
				}
			}
		}
	}

	private void randomiseEdges(Random random) {
		for (int i = 0; i < nodes.size(); i++) {
			Node node = nodes.get(i);
			List<Node> otherNodes = new ArrayList<Node>(nodes) {{ remove(node); }};
			otherNodes.sort(node.nearest());

			for (int j = 0; j < otherNodes.size(); j++) {
				Node otherNode = otherNodes.get(j);
				if (random.nextFloat() < 1.0 / (j * j + 1)) {
					edges.add(new LineSegment(node, otherNode));
				}
			}
		}
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
