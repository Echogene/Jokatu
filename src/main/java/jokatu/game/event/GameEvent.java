package jokatu.game.event;

import jokatu.game.user.player.Player;
import ophelia.collections.BaseCollection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An event is something that happens in a game to a certain collection of players.
 * @param <P>
 */
public interface GameEvent<P extends Player> {

	/**
	 * @return the private message as presented to the players for this event
	 */
	@NotNull
	String getMessageToPlayers();

	/**
	 * @return the public message as presented to all players and spectators
	 */
	@Nullable
	String getPublicMessage();

	/**
	 * @return the players for whom this has a private message
	 */
	@NotNull
	BaseCollection<P> getPlayers();
}
