package jokatu.game.result;

import jokatu.game.user.player.Player;

import java.util.Set;

/**
 * An endgame result for multiple players.  When a team wins, or multiple players draw, the set of players will have
 * more than one element.
 */
public class PlayerResult {

	private final Result result;
	private final Set<? extends Player> players;

	public PlayerResult(Result result, Set<? extends Player> players) {
		this.result = result;
		this.players = players;
	}
	public Result getResult() {
		return result;
	}

	public Set<? extends Player> getPlayers() {
		return players;
	}
}
