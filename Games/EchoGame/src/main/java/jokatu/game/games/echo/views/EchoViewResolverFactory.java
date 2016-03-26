package jokatu.game.games.echo.views;

import jokatu.game.games.echo.game.EchoGame;
import jokatu.game.games.echo.player.EchoPlayer;
import jokatu.game.viewresolver.ViewResolver;
import jokatu.game.viewresolver.ViewResolverFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author steven
 */
@Component
public class EchoViewResolverFactory extends ViewResolverFactory<EchoPlayer, EchoGame> {
	@NotNull
	@Override
	protected Class<EchoGame> handlesGame() {
		return EchoGame.class;
	}

	@NotNull
	@Override
	protected ViewResolver<EchoPlayer, EchoGame> getResolverFor(EchoGame castGame) {
		return new ViewResolver<EchoPlayer, EchoGame>(castGame) {
			@NotNull
			@Override
			protected ModelAndView getDefaultView() {
				return new ModelAndView("views/echo_view");
			}

			@NotNull
			@Override
			protected Class<EchoPlayer> handlesPlayer() {
				return EchoPlayer.class;
			}

			@NotNull
			@Override
			protected ModelAndView getViewFor(EchoPlayer player) {
				return getDefaultView();
			}
		};
	}
}
