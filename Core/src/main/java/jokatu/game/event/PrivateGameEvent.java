package jokatu.game.event;

import jokatu.game.player.Player;
import ophelia.collections.BaseCollection;
import org.jetbrains.annotations.NotNull;

/**
 * An event that occurs privately for a collection of players.
 * @author steven
 */
public interface PrivateGameEvent extends MessagedGameEvent {

	/**
	 * @return the players for whom this has a private message
	 */
	@NotNull
	BaseCollection<? extends Player> getPlayers();
}
