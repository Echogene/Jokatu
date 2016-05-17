package jokatu.game.viewresolver;

import jokatu.game.Game;
import jokatu.game.player.Player;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.ModelAndView;

import java.text.MessageFormat;

/**
 * Determine which view a player should see when requesting a game.
 * @author steven
 */
public abstract class ViewResolver<P extends Player, G extends Game<P>> {

	protected final G game;

	protected ViewResolver(@NotNull G game) {
		this.game = game;
	}

	@NotNull
	public final ModelAndView getViewForPlayer(@NotNull Player player) {
		P castPlayer = castPlayer(player);
		return getViewFor(castPlayer);
	}

	@NotNull
	public ModelAndView getViewForObserver() {
		return getDefaultView();
	}

	@NotNull
	protected abstract ModelAndView getDefaultView();

	private P castPlayer(@NotNull Player player) {
		Class<P> playerClass = getPlayerClass();
		if (!playerClass.isInstance(player)) {
			throw new IllegalArgumentException(
					MessageFormat.format("Player was not of the right type.  Expected {0}.", playerClass)
			);
		}
		return playerClass.cast(player);
	}

	@NotNull
	protected abstract Class<P> getPlayerClass();

	@NotNull
	protected abstract ModelAndView getViewFor(@NotNull P player);
}
