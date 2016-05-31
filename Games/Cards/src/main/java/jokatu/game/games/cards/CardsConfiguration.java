package jokatu.game.games.cards;

import jokatu.components.GameComponent;
import jokatu.components.config.GameConfiguration;
import jokatu.game.games.cards.game.CardGameFactory;
import jokatu.game.games.cards.input.CardInputDeserialiser;
import jokatu.game.games.cards.input.SkipInputDeserialiser;
import jokatu.game.games.cards.player.CardPlayerFactory;
import jokatu.game.games.cards.views.CardViewResolverFactory;
import jokatu.game.input.InputDeserialiser;
import jokatu.game.joining.JoinInputDeserialiser;
import jokatu.game.joining.finish.FinishJoiningInputDeserialiser;
import ophelia.collections.BaseCollection;
import ophelia.collections.set.UnmodifiableSet;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import static java.util.Arrays.asList;
import static jokatu.game.games.cards.game.CardGame.CARDS;

/**
 * @author steven
 */
@GameComponent(gameName = CARDS)
public class CardsConfiguration implements GameConfiguration {

	private final CardGameFactory echoGameFactory;
	private final CardPlayerFactory echoPlayerFactory;
	private final JoinInputDeserialiser joinInputDeserialiser;
	private final FinishJoiningInputDeserialiser finishJoiningInputDeserialiser;
	private final CardInputDeserialiser cardInputDeserialiser;
	private final SkipInputDeserialiser skipInputDeserialiser;
	private final CardViewResolverFactory echoViewResolverFactory;

	@Autowired
	public CardsConfiguration(CardGameFactory echoGameFactory,
	                          CardPlayerFactory echoPlayerFactory,
	                          JoinInputDeserialiser joinInputDeserialiser,
	                          FinishJoiningInputDeserialiser finishJoiningInputDeserialiser,
	                          CardInputDeserialiser cardInputDeserialiser,
	                          SkipInputDeserialiser skipInputDeserialiser,
	                          CardViewResolverFactory echoViewResolverFactory
	) {
		this.echoGameFactory = echoGameFactory;
		this.echoPlayerFactory = echoPlayerFactory;
		this.joinInputDeserialiser = joinInputDeserialiser;
		this.finishJoiningInputDeserialiser = finishJoiningInputDeserialiser;
		this.cardInputDeserialiser = cardInputDeserialiser;
		this.skipInputDeserialiser = skipInputDeserialiser;
		this.echoViewResolverFactory = echoViewResolverFactory;
	}

	@NotNull
	@Override
	public CardGameFactory getGameFactory() {
		return echoGameFactory;
	}

	@NotNull
	@Override
	public CardPlayerFactory getPlayerFactory() {
		return echoPlayerFactory;
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
	public CardViewResolverFactory getViewResolverFactory() {
		return echoViewResolverFactory;
	}
}
