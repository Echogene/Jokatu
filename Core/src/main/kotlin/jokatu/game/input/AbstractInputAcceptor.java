package jokatu.game.input;

import jokatu.game.event.GameEvent;
import jokatu.game.player.Player;
import ophelia.collections.set.Singleton;
import ophelia.event.observable.AbstractSynchronousObservable;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An abstract version of {@link InputAcceptor} that only accepts a specific type of input from a specific type of
 * player.  It does nothing with the input it is not the type it specifies.
 * @param <I> the type of the {@link Input} to accept
 * @param <P> the type of the {@link Player} to accept
 * @param <E> the type of {@link GameEvent} this can fire
 */
public abstract class AbstractInputAcceptor<I extends Input, P extends Player, E extends GameEvent>
		extends AbstractSynchronousObservable<E>
		implements InputAcceptor<E> {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@NotNull
	protected abstract Class<I> getInputClass();

	@NotNull
	private I castInput(@NotNull Input input) {
		return getInputClass().cast(input);
	}

	@NotNull
	protected abstract Class<P> getPlayerClass();

	@NotNull
	private P castPlayer(@NotNull Player player) {
		return getPlayerClass().cast(player);
	}

	@NotNull
	@Override
	public final Singleton<Class<? extends Input>> getAcceptedInputs() {
		return new Singleton<>(getInputClass());
	}

	@Override
	public final void accept(@NotNull Input input, @NotNull Player player) throws Exception {
		Class<P> playerClass = getPlayerClass();
		if (!playerClass.isInstance(player)) {
			log.debug(
					"Ignoring player {} because it was not a {}",
					input,
					playerClass.getSimpleName()
			);
			return;
		}
		Class<I> inputClass = getInputClass();
		if (!inputClass.isInstance(input)) {
			log.debug(
					"Ignoring input {} because it was not a {}",
					input,
					inputClass.getSimpleName()
			);
			return;
		}
		acceptCastInputAndPlayer(castInput(input), castPlayer(player));
	}

	protected abstract void acceptCastInputAndPlayer(@NotNull I input, @NotNull P inputter) throws Exception;
}
