package jokatu.game.input;

import jokatu.game.event.GameEvent;
import jokatu.game.player.Player;
import ophelia.event.observable.AbstractSynchronousObservable;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;

public abstract class InputAcceptor<I extends Input, P extends Player> extends AbstractSynchronousObservable<GameEvent> {

	protected abstract Class<I> getInputClass();

	private I castInput(Input input) {
		Class<I> inputClass = getInputClass();
		if (!inputClass.isInstance(input)) {
			throw new IllegalArgumentException(
					MessageFormat.format("Input was not of the right type.  Expected {0}", inputClass)
			);
		}
		return inputClass.cast(input);
	}

	protected abstract Class<P> getPlayerClass();

	private P castPlayer(Player player) {
		Class<P> playerClass = getPlayerClass();
		if (!playerClass.isInstance(player)) {
			throw new IllegalArgumentException(
					MessageFormat.format("Player was not of the right type.  Expected {0}", playerClass)
			);
		}
		return playerClass.cast(player);
	}

	public void accept(@NotNull Input input, @NotNull Player player) throws Exception {
		acceptCastInputAndPlayer(castInput(input), castPlayer(player));
	}

	protected abstract void acceptCastInputAndPlayer(I input, P inputter) throws Exception;
}
