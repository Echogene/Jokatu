package jokatu.game.games.echo;

import com.fasterxml.jackson.annotation.JsonValue;
import jokatu.game.event.GameEvent;
import ophelia.collections.BaseCollection;
import ophelia.collections.set.Singleton;
import org.jetbrains.annotations.NotNull;

public class Echo implements GameEvent<EchoPlayer> {

	private final String input;
	private final EchoPlayer player;

	public Echo(EchoInput input, EchoPlayer player) {
		this.input = input.getString();
		this.player = player;
	}

	@NotNull
	@Override
	public BaseCollection<EchoPlayer> getPlayers() {
		return new Singleton<>(player);
	}

	@JsonValue
	public String getInput() {
		return input;
	}
}
