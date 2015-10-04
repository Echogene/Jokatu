package jokatu.game.factory.game;

import jokatu.game.Game;
import jokatu.game.input.Input;
import jokatu.game.player.Player;
import org.jetbrains.annotations.NotNull;

/**
 * This creates games of a specific type.
 */
public interface GameFactory {

	@NotNull
	Game<? extends Player, ? extends Input> produce();
}
