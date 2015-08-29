package jokatu.game.event;

import jokatu.game.user.player.Player;
import ophelia.collections.BaseCollection;
import org.jetbrains.annotations.NotNull;

/**
 * @author steven
 */
public interface PrivateGameEvent extends GameEvent {

	/**
	 * @return the players for whom this has a private message
	 */
	@NotNull
	BaseCollection<? extends Player> getPlayers();
}
