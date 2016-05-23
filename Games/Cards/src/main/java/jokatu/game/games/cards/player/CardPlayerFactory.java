package jokatu.game.games.cards.player;

import jokatu.game.games.cards.game.CardGame;
import jokatu.game.player.PlayerFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class CardPlayerFactory extends PlayerFactory<CardPlayer, CardGame> {

	@NotNull
	@Override
	protected Class<CardGame> getGameClass() {
		return CardGame.class;
	}

	@NotNull
	@Override
	protected CardPlayer produceInCastGame(@NotNull CardGame cardGame, @NotNull String username) {
		return new CardPlayer(username);
	}
}
