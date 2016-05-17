package jokatu.game.viewresolver;

import jokatu.game.Game;
import jokatu.game.player.Player;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;

/**
 * A factory for {@link ViewResolver}s.
 * @author steven
 */
public abstract class ViewResolverFactory<P extends Player, G extends Game<P>> {

	@NotNull
	public final ViewResolver<P, G> getViewResolver(@NotNull Game<?> game) {
		G castGame = castGame(game);
		return getResolverFor(castGame);
	}

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
	protected abstract Class<G> getGameClass();

	@NotNull
	protected abstract ViewResolver<P, G> getResolverFor(@NotNull G castGame);
}
