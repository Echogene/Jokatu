package jokatu.game.input;

import jokatu.game.user.player.Player;

public interface Input<P extends Player> {

	/**
	 * @return the player that made this input.
	 */
	P getPlayer();
}
