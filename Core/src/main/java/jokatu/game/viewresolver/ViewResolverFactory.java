package jokatu.game.viewresolver;

import jokatu.game.Game;
import jokatu.game.player.Player;

/**
 * @author steven
 */
public interface ViewResolverFactory<P extends Player, G extends Game<P, ?>> {

	ViewResolver<P, G> getViewResolver(Game<?, ?> game);
}
