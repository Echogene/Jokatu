package jokatu.game.event;

import jokatu.game.user.player.Player;
import ophelia.collections.BaseCollection;
import ophelia.collections.UnmodifiableCollection;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public abstract class AbstractPrivateGameEvent implements PrivateGameEvent {

	protected final Collection<? extends Player> players;

	protected AbstractPrivateGameEvent(Collection<? extends Player> players) {
		this.players = players;
	}

	@Override
	@NotNull
	public BaseCollection<? extends Player> getPlayers() {
		return new UnmodifiableCollection<>(players);
	}
}
