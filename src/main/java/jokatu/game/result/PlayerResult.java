package jokatu.game.result;

import jokatu.game.user.player.Player;

import java.util.Collection;

/**
 * An endgame result for multiple players.  When a team wins, or multiple players draw, the set of players will have
 * more than one element.
 */
public class PlayerResult {

	private final Result result;
	private final Collection<? extends Player> players;

	public PlayerResult(Result result, Collection<? extends Player> players) {
		this.result = result;
		this.players = players;
	}
	public Result getResult() {
		return result;
	}

	public Collection<? extends Player> getPlayers() {
		return players;
	}
}
