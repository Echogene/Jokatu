package jokatu.game.games.echo.game;

import jokatu.game.event.PublicGameEvent;
import jokatu.game.games.echo.input.EchoInput;
import jokatu.game.player.StandardPlayer;
import org.jetbrains.annotations.NotNull;

public class Echo implements PublicGameEvent {

	private final String message;

	public Echo(EchoInput input, StandardPlayer player) {
		message = player.getName() + " said: " + input.getString();
	}

	@NotNull
	@Override
	public String getMessage() {
		return message;
	}
}
