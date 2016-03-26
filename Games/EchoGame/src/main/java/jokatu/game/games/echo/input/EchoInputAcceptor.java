package jokatu.game.games.echo.input;

import jokatu.game.event.AbstractPrivateGameEvent;
import jokatu.game.games.echo.Echo;
import jokatu.game.games.echo.player.EchoPlayer;
import jokatu.game.input.InputAcceptor;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class EchoInputAcceptor extends InputAcceptor<EchoInput, EchoPlayer> {
	@Override
	protected Class<EchoInput> getInputClass() {
		return EchoInput.class;
	}

	@Override
	protected Class<EchoPlayer> getPlayerClass() {
		return EchoPlayer.class;
	}

	@Override
	protected void acceptCastInputAndPlayer(EchoInput input, EchoPlayer inputter) throws Exception {
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
