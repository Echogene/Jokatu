package jokatu.game.event;

import jokatu.game.user.player.Player;
import ophelia.collections.BaseCollection;
import ophelia.collections.UnmodifiableCollection;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public abstract class AbstractGameEvent<P extends Player> implements GameEvent<P> {

	protected final Collection<P> players;

	protected AbstractGameEvent(Collection<P> players) {
		this.players = players;
	}

	@Override
	@NotNull
	public BaseCollection<P> getPlayers() {
		return new UnmodifiableCollection<>(players);
	}
}
