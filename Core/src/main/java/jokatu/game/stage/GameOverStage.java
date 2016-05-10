package jokatu.game.stage;

import jokatu.game.Stage;
import jokatu.game.input.Input;
import jokatu.game.input.InputAcceptor;
import jokatu.game.input.UnacceptableInputException;
import jokatu.game.player.Player;

public class GameOverStage extends Stage {
	public GameOverStage() {
		super(new InputAcceptor<Input, Player>() {
			@Override
			protected Class<Input> getInputClass() {
				return Input.class;
			}

			@Override
			protected Class<Player> getPlayerClass() {
				return Player.class;
			}

			@Override
			protected void acceptCastInputAndPlayer(Input input, Player inputter) throws Exception {
				throw new UnacceptableInputException("The game is over!");
			}
		});
	}
}
