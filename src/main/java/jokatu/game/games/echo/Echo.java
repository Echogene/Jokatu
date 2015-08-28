package jokatu.game.games.echo;

import jokatu.game.event.GameEvent;
import ophelia.collections.BaseCollection;
import ophelia.collections.set.Singleton;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Echo implements GameEvent<EchoPlayer> {

	private final String input;
	private final EchoPlayer player;

	public Echo(EchoInput input, EchoPlayer player) {
		this.input = input.getString();
		this.player = player;
	}

	@NotNull
	@Override
	public String getMessageToPlayers() {
		return "You said: " + input;
	}

	@Nullable
	@Override
	public String getPublicMessage() {
		return player.getName() + " said: " + input;
	}

	@NotNull
	@Override
	public BaseCollection<EchoPlayer> getPlayers() {
		return new Singleton<>(player);
	}
}
