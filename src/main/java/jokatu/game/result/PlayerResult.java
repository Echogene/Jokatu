package jokatu.game.result;

import jokatu.game.event.GameEvent;
import jokatu.game.user.player.Player;
import ophelia.collections.BaseCollection;
import ophelia.collections.UnmodifiableCollection;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * An endgame result for multiple players.  When a team wins, or multiple players draw, the set of players will have
 * more than one element.
 */
public class PlayerResult<P extends Player> implements GameEvent<P> {

	private final Result result;
	private final Collection<P> players;

	public PlayerResult(Result result, Collection<P> players) {
		this.result = result;
		this.players = players;
	}
	public Result getResult() {
		return result;
	}

	@Override
	@NotNull
	public BaseCollection<P> getPlayers() {
		return new UnmodifiableCollection<>(players);
	}
}
