package jokatu.game.stage;

import jokatu.game.Stage;
import jokatu.game.event.GameEvent;
import jokatu.game.input.Input;
import jokatu.game.input.UnacceptableInputException;
import jokatu.game.player.Player;
import jokatu.game.status.StandardTextStatus;
import ophelia.event.observable.AbstractSynchronousObservable;
import org.jetbrains.annotations.NotNull;

public class GameOverStage extends AbstractSynchronousObservable<GameEvent> implements Stage {

	public GameOverStage(StandardTextStatus status) {
		status.setText("Game over.");
	}

	@Override
	public void accept(@NotNull Input input, @NotNull Player player) throws Exception {
		throw new UnacceptableInputException("The game is over!");
	}
}
