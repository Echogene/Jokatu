package jokatu.game.factory.player;

import jokatu.game.player.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Given a username, this constructs a player for its respective game.
 * @param <P>
 */
public interface PlayerFactory<P extends Player> {

	@NotNull P produce(@NotNull String username);
}
