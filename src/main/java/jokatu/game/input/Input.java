package jokatu.game.input;

import jokatu.game.user.player.Player;
import org.jetbrains.annotations.NotNull;

public interface Input<P extends Player> {

	/**
	 * @return the player that made this input.
	 */
	@NotNull P getPlayer();
}
