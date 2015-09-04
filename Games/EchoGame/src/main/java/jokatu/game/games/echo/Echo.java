package jokatu.game.games.echo;

import jokatu.game.event.PublicGameEvent;
import org.jetbrains.annotations.NotNull;

public class Echo implements PublicGameEvent {

	private final String message;

	public Echo(EchoInput input, EchoPlayer player) {
		message = player.getName() + " said: " + input;
	}

	@NotNull
	@Override
	public String getMessage() {
		return message;
	}
}
