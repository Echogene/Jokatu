package jokatu.game.result;

import jokatu.game.event.PublicGameEvent;
import jokatu.game.event.StageOverEvent;
import jokatu.game.player.Player;
import ophelia.util.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * An endgame result for multiple players.  When a team wins, or multiple players draw, the set of players will have
 * more than one element.
 */
public class PlayerResult extends StageOverEvent implements PublicGameEvent {

	private final String message;

	public PlayerResult(Result result, Collection<? extends Player> players) {
		if (players.size() == 1) {
			message = players.iterator().next() + " " + result.toString();
		} else {
			Set<String> playerNames = players.stream()
					.map(Player::getName)
					.collect(Collectors.toSet());
			message = StringUtils.join(playerNames, ", ") + " " + result.toString();
		}
	}

	@NotNull
	@Override
	public String getMessage() {
		return message;
	}
}
