package jokatu.game.games.sevens;

import jokatu.components.GameComponent;
import jokatu.components.config.GameConfiguration;
import jokatu.game.games.sevens.game.SevensGameFactory;
import jokatu.game.games.sevens.input.CardInputDeserialiser;
import jokatu.game.games.sevens.input.SkipInputDeserialiser;
import jokatu.game.games.sevens.player.SevensPlayerFactory;
import jokatu.game.games.sevens.views.SevensViewResolverFactory;
import jokatu.game.input.InputDeserialiser;
import jokatu.game.joining.JoinInputDeserialiser;
import jokatu.game.joining.finish.FinishJoiningInputDeserialiser;
import ophelia.collections.BaseCollection;
import ophelia.collections.set.UnmodifiableSet;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import static java.util.Arrays.asList;
import static jokatu.game.games.sevens.game.SevensGame.SEVENS;

/**
 * @author steven
 */
@GameComponent(gameName = SEVENS)
public class SevensConfiguration implements GameConfiguration {

	private final SevensGameFactory sevensGameFactory;
	private final SevensPlayerFactory sevensPlayerFactory;
	private final JoinInputDeserialiser joinInputDeserialiser;
	private final FinishJoiningInputDeserialiser finishJoiningInputDeserialiser;
	private final CardInputDeserialiser cardInputDeserialiser;
	private final SkipInputDeserialiser skipInputDeserialiser;
	private final SevensViewResolverFactory sevensViewResolverFactory;

	@Autowired
	public SevensConfiguration(SevensGameFactory sevensGameFactory,
	                           SevensPlayerFactory sevensPlayerFactory,
	                           JoinInputDeserialiser joinInputDeserialiser,
	                           FinishJoiningInputDeserialiser finishJoiningInputDeserialiser,
	                           CardInputDeserialiser cardInputDeserialiser,
	                           SkipInputDeserialiser skipInputDeserialiser,
	                           SevensViewResolverFactory sevensViewResolverFactory
	) {
		this.sevensGameFactory = sevensGameFactory;
		this.sevensPlayerFactory = sevensPlayerFactory;
		this.joinInputDeserialiser = joinInputDeserialiser;
		this.finishJoiningInputDeserialiser = finishJoiningInputDeserialiser;
		this.cardInputDeserialiser = cardInputDeserialiser;
		this.skipInputDeserialiser = skipInputDeserialiser;
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
	public BaseCollection<? extends InputDeserialiser> getInputDeserialisers() {
		return new UnmodifiableSet<>(asList(
				joinInputDeserialiser, finishJoiningInputDeserialiser, cardInputDeserialiser, skipInputDeserialiser
		));
	}

	@NotNull
	@Override
	public SevensViewResolverFactory getViewResolverFactory() {
		return sevensViewResolverFactory;
	}
}
