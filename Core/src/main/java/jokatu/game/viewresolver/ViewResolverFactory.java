package jokatu.game.viewresolver;

import jokatu.game.Game;
import jokatu.game.player.Player;
import org.jetbrains.annotations.NotNull;

/**
 * @author steven
 */
public interface ViewResolverFactory<P extends Player, G extends Game<P, ?>> {

	@NotNull
	ViewResolver<P, G> getViewResolver(Game<?, ?> game);
}
