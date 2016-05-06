package jokatu.game.input;

import jokatu.game.event.GameEvent;
import jokatu.game.player.Player;
import ophelia.event.observable.AbstractSynchronousObservable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;

public abstract class InputAcceptor<I extends Input, P extends Player> extends AbstractSynchronousObservable<GameEvent> {

	private final Log log = LogFactory.getLog(getClass());

	protected abstract Class<I> getInputClass();

	private I castInput(Input input) {
		return getInputClass().cast(input);
	}

	protected abstract Class<P> getPlayerClass();

	private P castPlayer(Player player) {
		return getPlayerClass().cast(player);
	}

	public void accept(@NotNull Input input, @NotNull Player player) throws Exception {
		Class<P> playerClass = getPlayerClass();
		if (!playerClass.isInstance(player)) {
			log.debug(MessageFormat.format(
					"Ignoring player {0} because it was not a {1}",
					input,
					playerClass.getSimpleName()
			));
			return;
		}
		Class<I> inputClass = getInputClass();
		if (!inputClass.isInstance(input)) {
			log.debug(MessageFormat.format(
					"Ignoring input {0} because it was not a {1}",
					input,
					inputClass.getSimpleName()
			));
			return;
		}
		acceptCastInputAndPlayer(castInput(input), castPlayer(player));
	}

	protected abstract void acceptCastInputAndPlayer(I input, P inputter) throws Exception;
}
