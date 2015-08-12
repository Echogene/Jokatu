package jokatu.game.result;

import jokatu.game.event.GameEvent;
import jokatu.game.user.player.Player;
import ophelia.collections.BaseCollection;
import ophelia.collections.UnmodifiableCollection;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class AbstractGameEvent<P extends Player> implements GameEvent<P> {

	protected final Collection<P> players;

	public AbstractGameEvent(Collection<P> players) {
		this.players = players;
	}

	@Override
	@NotNull
	public BaseCollection<P> getPlayers() {
		return new UnmodifiableCollection<>(players);
	}
}
