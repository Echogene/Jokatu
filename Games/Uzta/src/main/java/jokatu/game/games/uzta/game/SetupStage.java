package jokatu.game.games.uzta.game;

import jokatu.game.event.GameEvent;
import jokatu.game.games.uzta.event.GraphUpdatedEvent;
import jokatu.game.games.uzta.graph.Node;
import jokatu.game.games.uzta.input.RandomiseGraphInput;
import jokatu.game.player.StandardPlayer;
import jokatu.game.stage.SingleInputStage;
import ophelia.collections.BaseCollection;
import ophelia.collections.pair.UnorderedPair;
import ophelia.collections.set.EmptySet;
import ophelia.collections.set.HashSet;
import ophelia.collections.set.UnmodifiableSet;
import ophelia.graph.BiGraph;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.Set;

public class SetupStage extends SingleInputStage<RandomiseGraphInput, StandardPlayer, GameEvent> {

	private final Set<Node> nodes = new HashSet<>();

	private final BiGraph<Node, UnorderedPair<Node>> graph = new BiGraph<Node, UnorderedPair<Node>>() {
		@Override
		public BaseCollection<UnorderedPair<Node>> getEdges() {
			return EmptySet.emptySet();
		}

		@Override
		public BaseCollection<Node> getNodes() {
			return new UnmodifiableSet<>(nodes);
		}
	};

	SetupStage() {
		randomiseGraph();
	}

	private void randomiseGraph() {
		nodes.clear();
		Random random = new Random();
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

	private boolean isFarEnoughAnotherNode(Node node) {
		return nodes.stream()
				.allMatch(existingNode -> existingNode.distanceFrom(node) > 10);
	}

	@Override
	public void start() {
		fireEvent(new GraphUpdatedEvent(graph));
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

	}
}
