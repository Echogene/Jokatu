package jokatu.game.games.cards.player;

import jokatu.game.games.cards.game.CardGame;
import jokatu.game.player.AbstractPlayerFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class CardPlayerFactory extends AbstractPlayerFactory<CardPlayer, CardGame> {

	@NotNull
	@Override
	protected Class<CardGame> getGameClass() {
		return CardGame.class;
	}

	@NotNull
	@Override
	protected CardPlayer produceInCastGame(@NotNull CardGame cardGame, @NotNull String username) {
		CardPlayer player = new CardPlayer(username);
		cardGame.register(player);
		return player;
	}
}
