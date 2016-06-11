package jokatu.game.games.uzta.game;

import jokatu.game.event.GameEvent;
import jokatu.game.games.uzta.event.GraphUpdatedEvent;
import jokatu.game.games.uzta.input.RandomiseGraphInput;
import jokatu.game.player.StandardPlayer;
import jokatu.game.stage.SingleInputStage;
import ophelia.collections.BaseCollection;
import ophelia.collections.pair.UnorderedPair;
import ophelia.collections.set.EmptySet;
import ophelia.graph.BiGraph;
import org.jetbrains.annotations.NotNull;

public class SetupStage extends SingleInputStage<RandomiseGraphInput, StandardPlayer, GameEvent> {

	private final BiGraph<String, UnorderedPair<String>> graph = new BiGraph<String, UnorderedPair<String>>() {
		@Override
		public BaseCollection<UnorderedPair<String>> getEdges() {
			return EmptySet.emptySet();
		}

		@Override
		public BaseCollection<String> getNodes() {
			return EmptySet.emptySet();
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
