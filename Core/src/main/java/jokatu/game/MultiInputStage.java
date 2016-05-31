package jokatu.game;

import jokatu.game.event.GameEvent;
import jokatu.game.input.Input;
import jokatu.game.input.InputAcceptor;
import jokatu.game.player.Player;
import ophelia.event.observable.AbstractSynchronousObservable;
import ophelia.exceptions.StackedException;
import ophelia.exceptions.voidmaybe.VoidMaybe;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class MultiInputStage extends AbstractSynchronousObservable<GameEvent> implements Stage<GameEvent> {

	private final List<InputAcceptor<? extends Input, ? extends Player, ? extends GameEvent>> inputAcceptors = new ArrayList<>();

	protected void addInputAcceptor(InputAcceptor<? extends Input, ? extends Player, ? extends GameEvent> inputAcceptor) {
		inputAcceptors.add(inputAcceptor);
		inputAcceptor.observe(this::fireEvent);
	}

	@Override
	public void accept(@NotNull Input input, @NotNull Player player) throws StackedException {
		VoidMaybe.mergeFailures(
				inputAcceptors.stream()
						.map(VoidMaybe.wrapOutput(acceptor -> acceptor.accept(input, player)))
						.collect(Collectors.toList())
		).throwOnFailure();
	}
}
