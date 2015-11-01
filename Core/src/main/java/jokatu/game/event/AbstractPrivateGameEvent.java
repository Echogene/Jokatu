package jokatu.game.event;

import jokatu.game.player.Player;
import ophelia.collections.BaseCollection;
import ophelia.collections.UnmodifiableCollection;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * An abstract version of a private game event that stores the collection of players for whom it is private.
 */
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
