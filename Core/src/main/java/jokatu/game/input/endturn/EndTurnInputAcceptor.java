package jokatu.game.input.endturn;

import jokatu.game.input.AnyEventInputAcceptor;
import jokatu.game.player.Player;
import jokatu.game.turn.TurnChangedEvent;
import jokatu.game.turn.TurnManager;
import org.jetbrains.annotations.NotNull;

public class EndTurnInputAcceptor<P extends Player> extends AnyEventInputAcceptor<EndTurnInput, P> {

	private final TurnManager<P> turnManager;
	private final Class<P> playerClass;

	public EndTurnInputAcceptor(@NotNull TurnManager<P> turnManager, @NotNull Class<P> playerClass) {
		this.turnManager = turnManager;
		this.playerClass = playerClass;
	}

	@NotNull
	@Override
	protected final Class<EndTurnInput> getInputClass() {
		return EndTurnInput.class;
	}

	@NotNull
	@Override
	protected final Class<P> getPlayerClass() {
		return playerClass;
	}

	@Override
	protected final void acceptCastInputAndPlayer(@NotNull EndTurnInput input, @NotNull P inputter) throws Exception {
		turnManager.assertCurrentPlayer(inputter);

		fireAdditionalEvents(inputter);

		turnManager.next();
	}

	/**
	 * Override this method to fire additional events.  It happens after we know that the current turn is actually the
	 * inputter's, but before the {@link TurnChangedEvent} for the next turn is fired.
	 *
	 * @param inputter the player that ended their turn
	 */
	protected void fireAdditionalEvents(@NotNull P inputter) {
		// Override me!
	}
}
