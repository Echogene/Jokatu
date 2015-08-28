package jokatu.game.result;

import jokatu.game.event.AbstractGameEvent;
import jokatu.game.user.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * An endgame result for multiple players.  When a team wins, or multiple players draw, the set of players will have
 * more than one element.
 */
public class PlayerResult<P extends Player> extends AbstractGameEvent<P> {

	private final Result result;

	public PlayerResult(Result result, Collection<P> players) {
		super(players);
		this.result = result;
	}

	@NotNull
	@Override
	public String getMessageToPlayers() {
		return getPublicMessage();
	}

	@NotNull
	@Override
	public String getPublicMessage() {
		return players.toString() + " " + result.toString();
	}
}
