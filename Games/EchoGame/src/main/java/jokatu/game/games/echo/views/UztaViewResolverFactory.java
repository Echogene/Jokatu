package jokatu.game.games.echo.views;

import jokatu.game.games.echo.game.EchoGame;
import jokatu.game.player.StandardPlayer;
import jokatu.game.viewresolver.ViewResolver;
import jokatu.game.viewresolver.ViewResolverFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author steven
 */
@Component
public class UztaViewResolverFactory extends ViewResolverFactory<StandardPlayer, EchoGame> {
	@NotNull
	@Override
	protected Class<EchoGame> getGameClass() {
		return EchoGame.class;
	}

	@NotNull
	@Override
	protected ViewResolver<StandardPlayer, EchoGame> getResolverFor(@NotNull EchoGame castGame) {
		return new ViewResolver<StandardPlayer, EchoGame>(castGame) {
			@NotNull
			@Override
			protected ModelAndView getDefaultView() {
				return new ModelAndView("views/echo_view");
			}

			@NotNull
			@Override
			protected Class<StandardPlayer> getPlayerClass() {
				return StandardPlayer.class;
			}

			@NotNull
			@Override
			protected ModelAndView getViewFor(@NotNull StandardPlayer player) {
				return getDefaultView();
			}
		};
	}
}
