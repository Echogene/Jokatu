package jokatu.game.viewresolver;

import jokatu.game.Game;
import jokatu.game.player.Player;
import org.springframework.web.servlet.ModelAndView;

import java.text.MessageFormat;

/**
 * @author steven
 */
public abstract class ViewResolver<P extends Player, G extends Game<P, ?>> {

	protected final G game;

	protected ViewResolver(G game) {
		this.game = game;
	}

	public ModelAndView getViewForPlayer(Player player) {
		P castPlayer = castPlayer(player);
		return getViewFor(castPlayer);
	}

	public abstract ModelAndView getViewForObserver();

	private P castPlayer(Player player) {
		Class<P> playerClass = handlesPlayer();
		if (!playerClass.isInstance(player)) {
			throw new IllegalArgumentException(
					MessageFormat.format("Player was not of the right type.  Expected {0}", playerClass)
			);
		}
		return playerClass.cast(player);
	}

	protected abstract Class<P> handlesPlayer();

	protected abstract ModelAndView getViewFor(P player);
}
