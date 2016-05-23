package jokatu.game.player;

import jokatu.game.Game;
import org.jetbrains.annotations.NotNull;

public interface PlayerFactory<P extends Player> {
	@NotNull
	P produce(@NotNull Game<? extends P> game, @NotNull String username);
}
