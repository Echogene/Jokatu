package jokatu.game.viewresolver;

import jokatu.game.Game;
import jokatu.game.player.Player;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;

/**
 * A factory for {@link ViewResolver}s.
 * @author steven
 */
public abstract class ViewResolverFactory<P extends Player, G extends Game<P, ?>> {

	@NotNull
	public final ViewResolver<P, G> getViewResolver(Game<?, ?> game) {
		G castGame = castGame(game);
		return getResolverFor(castGame);
	}

	@NotNull
	private G castGame(Game<?, ?> game) {
		Class<G> gameClass = handlesGame();
		if (!gameClass.isInstance(game)) {
			throw new IllegalArgumentException(
					MessageFormat.format("Game was not of the right type.  Expected {0}", gameClass)
			);
		}
		return gameClass.cast(game);
	}

	@NotNull
	protected abstract Class<G> handlesGame();

	@NotNull
	protected abstract ViewResolver<P, G> getResolverFor(G castGame);
}
