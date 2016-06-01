package jokatu.game.input;

import jokatu.game.event.GameEvent;
import jokatu.game.player.Player;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Fire one of these from a game to signify that a collection of players needs to provide input.  Firing one will
 * override any previously fired ones, so the given collection should be considered as the definitive source of players
 * that need to input.
 */
public class AwaitingInputEvent implements GameEvent {
	private final Collection<? extends Player> players;

	public AwaitingInputEvent(@NotNull Collection<? extends Player> players) {
		this.players = players;
	}

	public AwaitingInputEvent(@NotNull Player player) {
		this(Collections.singleton(player));
	}

	/**
	 * @return the players from whom we're awaiting input.
	 */
	@NotNull
	public Collection<? extends Player> getAwaitingPlayers() {
		return players;
	}

	@NotNull
	@Override
	public String getMessage() {
		return MessageFormat.format(
				"Awaiting input from ",
				players.stream().map(Player::getName).collect(Collectors.joining(", "))
		);
	}
}
