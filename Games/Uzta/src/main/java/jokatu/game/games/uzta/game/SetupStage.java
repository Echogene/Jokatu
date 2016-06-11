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
import ophelia.collections.set.UnmodifiableSet;
import ophelia.graph.BiGraph;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class SetupStage extends SingleInputStage<RandomiseGraphInput, StandardPlayer, GameEvent> {

	private final BiGraph<Node, UnorderedPair<Node>> graph = new BiGraph<Node, UnorderedPair<Node>>() {
		@Override
		public BaseCollection<UnorderedPair<Node>> getEdges() {
			return EmptySet.emptySet();
		}

		@Override
		public BaseCollection<Node> getNodes() {
			return new UnmodifiableSet<>(Arrays.asList(
				new Node("0", 10, 10),
				new Node("1", 90, 10)
			));
		}
	};

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
