package jokatu.game.factory.game;

import jokatu.game.Game;
import jokatu.game.input.Input;
import jokatu.game.player.Player;
import org.jetbrains.annotations.NotNull;

/**
 * This creates games of a specific type.
 */
public interface GameFactory {

	/**
	 * @param creatorName the name of the user who is creating the game.  They can get special options while setting up
	 *                    the game.  They don't necessarily need to join the game.
	 * @return a new game
	 */
	@NotNull
	Game<? extends Player, ? extends Input> produceGame(@NotNull String creatorName);
}
