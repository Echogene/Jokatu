package jokatu.game.viewresolver;

import jokatu.game.player.Player;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.ModelAndView;

import java.text.MessageFormat;

/**
 * Determine which view a player should see when requesting a game.
 * @author steven
 */
public abstract class ViewResolver<P extends Player, G extends jokatu.identity.Identifiable<jokatu.game.GameID> & ophelia.event.observable.Observable<jokatu.game.event.GameEvent>> {

	protected final G game;

	protected ViewResolver(G game) {
		this.game = game;
	}

	public final ModelAndView getViewForPlayer(Player player) {
		P castPlayer = castPlayer(player);
		return getViewFor(castPlayer);
	}

	public ModelAndView getViewForObserver() {
		return getDefaultView();
	}

	@NotNull
	protected abstract ModelAndView getDefaultView();

	private P castPlayer(Player player) {
		Class<P> playerClass = handlesPlayer();
		if (!playerClass.isInstance(player)) {
			throw new IllegalArgumentException(
					MessageFormat.format("Player was not of the right type.  Expected {0}.", playerClass)
			);
		}
		return playerClass.cast(player);
	}

	@NotNull
	protected abstract Class<P> handlesPlayer();

	@NotNull
	protected abstract ModelAndView getViewFor(P player);
}
