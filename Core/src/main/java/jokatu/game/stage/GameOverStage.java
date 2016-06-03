package jokatu.game.stage;

import jokatu.game.event.GameEvent;
import jokatu.game.input.Input;
import jokatu.game.input.UnacceptableInputException;
import jokatu.game.player.Player;
import jokatu.game.status.StandardTextStatus;
import ophelia.event.observable.AbstractSynchronousObservable;
import org.jetbrains.annotations.NotNull;

/**
 * A {@link Stage} that happens after the game is over.  No more input should be accepted at this point.
 */
public class GameOverStage extends AbstractSynchronousObservable<GameEvent> implements Stage<GameEvent> {

	public GameOverStage(StandardTextStatus status) {
		status.setText("Game over.");
	}

	@Override
	public void accept(@NotNull Input input, @NotNull Player player) throws Exception {
		throw new UnacceptableInputException("The game is over!");
	}
}
