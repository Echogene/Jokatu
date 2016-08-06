package jokatu.game;

import jokatu.game.event.GameEvent;
import jokatu.game.input.Input;
import jokatu.game.input.InputAcceptor;
import jokatu.game.player.Player;
import jokatu.game.stage.Stage;
import ophelia.collections.BaseCollection;
import ophelia.collections.UnmodifiableCollection;
import ophelia.event.observable.AbstractSynchronousObservable;
import ophelia.exceptions.StackedException;
import ophelia.exceptions.voidmaybe.VoidMaybe;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A stage that can accept multiple types of {@link Input}s by using some {@link InputAcceptor}s
 */
public abstract class MultiInputStage extends AbstractSynchronousObservable<GameEvent> implements Stage<GameEvent> {

	private final List<InputAcceptor<? extends GameEvent>> inputAcceptors = new ArrayList<>();

	protected void addInputAcceptor(InputAcceptor<? extends GameEvent> inputAcceptor) {
		inputAcceptors.add(inputAcceptor);
		inputAcceptor.observe(this::fireEvent);
	}

	@NotNull
	@Override
	public UnmodifiableCollection<Class<? extends Input>> getAcceptedInputs() {
		Set<Class<? extends Input>> inputs = inputAcceptors.stream()
				.map(InputAcceptor::getAcceptedInputs)
				.flatMap(BaseCollection::stream)
				.collect(Collectors.toSet());
		return new UnmodifiableCollection<>(inputs);
	}

	@Override
	public final void accept(@NotNull Input input, @NotNull Player player) throws StackedException {
		VoidMaybe.mergeFailures(
				inputAcceptors.stream()
						.map(VoidMaybe.wrap(acceptor -> acceptor.accept(input, player)))
						.collect(Collectors.toList())
		).throwOnFailure();
	}
}
