package jokatu.game.player;

import jokatu.game.Game;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;

public abstract class AbstractPlayerFactory<P extends Player, G extends Game<P>> implements PlayerFactory<P> {

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

	@Override
	@NotNull
	public final P produce(@NotNull Game<?> game, @NotNull String username) {
		return produceInCastGame(castGame(game), username);
	}

	@NotNull
	protected abstract P produceInCastGame(@NotNull G g, @NotNull String username);
}
