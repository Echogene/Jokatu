package jokatu.game.viewresolver;

import jokatu.game.Game;
import jokatu.game.player.Player;
import org.jetbrains.annotations.NotNull;
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

	public final ModelAndView getViewForPlayer(Player player) {
		P castPlayer = castPlayer(player);
		ModelAndView modelAndView = getViewFor(castPlayer);
		modelAndView.addObject("game", game);
		return modelAndView;
	}

	public ModelAndView getViewForObserver() {
		ModelAndView modelAndView = getDefaultView();
		modelAndView.addObject("game", game);
		return modelAndView;
	}

	@NotNull
	protected abstract ModelAndView getDefaultView();

	private P castPlayer(Player player) {
		Class<P> playerClass = handlesPlayer();
		if (!playerClass.isInstance(player)) {
			throw new IllegalArgumentException(
					MessageFormat.format("Player was not of the right type.  Expected {0}", playerClass)
			);
		}
		return playerClass.cast(player);
	}

	@NotNull
	protected abstract Class<P> handlesPlayer();

	@NotNull
	protected abstract ModelAndView getViewFor(P player);
}
