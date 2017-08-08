package jokatu.game.result;

import jokatu.game.event.PublicGameEvent;
import jokatu.game.event.StageOverEvent;
import jokatu.game.player.Player;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * An endgame result for multiple players.  When a team wins, or multiple players draw, the set of players will have
 * more than one element.
 */
public class PlayerResult extends StageOverEvent implements PublicGameEvent {

	private final String message;

	public PlayerResult(Result result, Collection<? extends Player> players) {

		String playerNames = players.stream()
				.map(Player::getName)
				.collect(Collectors.joining(", "));
		message = MessageFormat.format("{0} {1}.", playerNames, result.get3rdPersonPresent(players.size()));
	}

	@NotNull
	@Override
	public String getMessage() {
		return message;
	}
}
