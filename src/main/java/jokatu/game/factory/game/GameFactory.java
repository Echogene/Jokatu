package jokatu.game.factory.game;

import jokatu.game.Game;
import jokatu.game.input.Input;
import jokatu.game.user.player.Player;
import org.jetbrains.annotations.NotNull;

public interface GameFactory {

	@NotNull
	Game<? extends Player, ? extends Input> produce();
}
