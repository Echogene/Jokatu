package jokatu.game.games.echo.game;

import jokatu.game.event.AbstractPrivateGameEvent;
import jokatu.game.games.echo.input.EchoInput;
import jokatu.game.input.InputAcceptor;
import jokatu.game.player.StandardPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

class EchoStage extends InputAcceptor<EchoInput, StandardPlayer> {
	@NotNull
	@Override
	protected Class<EchoInput> getInputClass() {
		return EchoInput.class;
	}

	@NotNull
	@Override
	protected Class<StandardPlayer> getPlayerClass() {
		return StandardPlayer.class;
	}

	@Override
	protected void acceptCastInputAndPlayer(@NotNull EchoInput input, @NotNull StandardPlayer inputter) throws Exception {
		fireEvent(new Echo(input, inputter));
		fireEvent(new AbstractPrivateGameEvent(Collections.singleton(inputter)) {
			@NotNull
			@Override
			public String getMessage() {
				return "You said: " + input.getString();
			}
		});
	}
}
