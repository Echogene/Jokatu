package jokatu.game;

import jokatu.game.event.GameEvent;
import jokatu.game.input.Input;
import jokatu.game.input.InputAcceptor;
import jokatu.game.player.Player;
import ophelia.collections.BaseCollection;
import ophelia.collections.set.Singleton;
import ophelia.event.observable.AbstractSynchronousObservable;
import ophelia.exceptions.StackedException;
import ophelia.exceptions.voidmaybe.VoidMaybe;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;

public abstract class Stage extends AbstractSynchronousObservable<GameEvent> {

	private final BaseCollection<InputAcceptor<? extends Input, ? extends Player>> inputAcceptors;

	protected Stage(BaseCollection<InputAcceptor<? extends Input, ? extends Player>> inputAcceptors) {
		this.inputAcceptors = inputAcceptors;
		// Forward events from all the acceptors.
		inputAcceptors.stream()
				.forEach(inputAcceptor -> inputAcceptor.observe(this::fireEvent));
	}

	protected Stage(InputAcceptor<? extends Input, ? extends Player> inputAcceptor) {
		this(new Singleton<>(inputAcceptor));
	}

	void accept(@NotNull Input input, @NotNull Player player) throws StackedException {
		VoidMaybe.mergeFailures(
				inputAcceptors.stream()
						.map(VoidMaybe.wrapOutput(acceptor -> acceptor.accept(input, player)))
						.collect(Collectors.toList())
		).throwOnFailure();
	}
}
