package jokatu.game.player;

import jokatu.game.Game;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;

/**
 * Given a username, this constructs a player for its respective game.
 * @param <P>
 */
public abstract class PlayerFactory<P extends Player, G extends Game<P>> {

	@NotNull
	protected abstract Class<G> getGameClass();

	@NotNull
	private G castGame(@NotNull Game<?> game) {
		Class<G> gameClass = getGameClass();
		if (!gameClass.isInstance(game)) {
			throw new IllegalArgumentException(
					MessageFormat.format("Game was not of the right type.  Expected {0}.", gameClass)
			);
		}
		return gameClass.cast(game);
	}

	@NotNull
	public final P produce(@NotNull Game<? extends P> game, @NotNull String username) {
		return produceInCastGame(castGame(game), username);
	}

	@NotNull
	protected abstract P produceInCastGame(G g, String username);
}
