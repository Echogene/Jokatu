package jokatu.game;

import jokatu.game.player.Player;
import org.jetbrains.annotations.NotNull;

/**
 * This creates games of a specific type.
 */
public interface GameFactory<G extends Game<? extends Player>> {

	/**
	 * @param creatorName the name of the user who is creating the game.  They can get special options while setting up
	 *                    the game.  They don't necessarily need to join the game.
	 * @return a new game
	 */
	@NotNull
	G produceGame(@NotNull String creatorName);
}
