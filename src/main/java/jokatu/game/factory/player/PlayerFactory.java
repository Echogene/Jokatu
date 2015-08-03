package jokatu.game.factory.player;

import jokatu.game.user.player.Player;
import org.jetbrains.annotations.NotNull;

public interface PlayerFactory<P extends Player> {

	@NotNull P produce(@NotNull String username);
}
