package jokatu.game.games.cards.game;

import jokatu.game.AbstractGameFactory;
import jokatu.game.GameID;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class CardGameFactory extends AbstractGameFactory<CardGame> {

	@NotNull
	@Override
	protected CardGame produce(@NotNull GameID gameID, @NotNull String creatorName) {
		return new CardGame(gameID);
	}
}
