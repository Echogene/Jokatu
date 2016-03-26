package jokatu.game.games.echo.game;

import jokatu.game.event.PublicGameEvent;
import jokatu.game.games.echo.input.EchoInput;
import jokatu.game.games.echo.player.EchoPlayer;
import org.jetbrains.annotations.NotNull;

public class Echo implements PublicGameEvent {

	private final String message;

	public Echo(EchoInput input, EchoPlayer player) {
		message = player.getName() + " said: " + input.getString();
	}

	@NotNull
	@Override
	public String getMessage() {
		return message;
	}
}
