package jokatu.game.stage;

import jokatu.game.MultiInputStage;
import jokatu.game.input.Input;
import jokatu.game.input.InputAcceptor;
import jokatu.game.input.UnacceptableInputException;
import jokatu.game.player.Player;
import jokatu.game.status.StandardTextStatus;
import org.jetbrains.annotations.NotNull;

public class GameOverStage extends MultiInputStage {
	public GameOverStage(StandardTextStatus status) {
		super(new InputAcceptor<Input, Player>() {
			@NotNull
			@Override
			protected Class<Input> getInputClass() {
				return Input.class;
			}

			@NotNull
			@Override
			protected Class<Player> getPlayerClass() {
				return Player.class;
			}

			@Override
			protected void acceptCastInputAndPlayer(@NotNull Input input, @NotNull Player inputter) throws Exception {
				throw new UnacceptableInputException("The game is over!");
			}
		});
		status.setText("Game over.");
	}
}
