package jokatu.game.games.sevens;

import jokatu.components.GameComponent;
import jokatu.components.config.GameConfiguration;
import jokatu.game.games.sevens.game.SevensGameFactory;
import jokatu.game.games.sevens.player.SevensPlayerFactory;
import jokatu.game.games.sevens.views.SevensViewResolverFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import static jokatu.game.games.sevens.game.SevensGame.SEVENS;

/**
 * @author steven
 */
@GameComponent(gameName = SEVENS)
public class SevensConfiguration implements GameConfiguration {

	private final SevensGameFactory sevensGameFactory;
	private final SevensPlayerFactory sevensPlayerFactory;
	private final SevensViewResolverFactory sevensViewResolverFactory;

	@Autowired
	public SevensConfiguration(SevensGameFactory sevensGameFactory,
	                           SevensPlayerFactory sevensPlayerFactory,
	                           SevensViewResolverFactory sevensViewResolverFactory
	) {
		this.sevensGameFactory = sevensGameFactory;
		this.sevensPlayerFactory = sevensPlayerFactory;
		this.sevensViewResolverFactory = sevensViewResolverFactory;
	}

	@NotNull
	@Override
	public SevensGameFactory getGameFactory() {
		return sevensGameFactory;
	}

	@NotNull
	@Override
	public SevensPlayerFactory getPlayerFactory() {
		return sevensPlayerFactory;
	}

	@NotNull
	@Override
	public SevensViewResolverFactory getViewResolverFactory() {
		return sevensViewResolverFactory;
	}
}
