package jokatu.game.games.cards;

import jokatu.components.GameComponent;
import jokatu.components.config.GameConfiguration;
import jokatu.game.games.cards.game.CardGameFactory;
import jokatu.game.games.cards.input.CardInputDeserialiser;
import jokatu.game.games.cards.player.CardPlayerFactory;
import jokatu.game.games.cards.views.CardViewResolverFactory;
import jokatu.game.input.InputDeserialiser;
import jokatu.game.joining.JoinInputDeserialiser;
import ophelia.collections.BaseCollection;
import ophelia.collections.pair.UnorderedPair;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import static jokatu.game.games.cards.game.CardGame.CARDS;

/**
 * @author steven
 */
@GameComponent(gameName = CARDS)
public class CardsConfiguration implements GameConfiguration {

	private final CardGameFactory echoGameFactory;
	private final CardPlayerFactory echoPlayerFactory;
	private final JoinInputDeserialiser joinInputDeserialiser;
	private final CardInputDeserialiser echoInputDeserialiser;
	private final CardViewResolverFactory echoViewResolverFactory;

	@Autowired
	public CardsConfiguration(CardGameFactory echoGameFactory,
	                          CardPlayerFactory echoPlayerFactory,
	                          JoinInputDeserialiser joinInputDeserialiser,
	                          CardInputDeserialiser echoInputDeserialiser,
	                          CardViewResolverFactory echoViewResolverFactory
	) {
		this.echoGameFactory = echoGameFactory;
		this.echoPlayerFactory = echoPlayerFactory;
		this.joinInputDeserialiser = joinInputDeserialiser;
		this.echoInputDeserialiser = echoInputDeserialiser;
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
		return new UnorderedPair<>(joinInputDeserialiser, echoInputDeserialiser);
	}

	@NotNull
	@Override
	public CardViewResolverFactory getViewResolverFactory() {
		return echoViewResolverFactory;
	}
}
