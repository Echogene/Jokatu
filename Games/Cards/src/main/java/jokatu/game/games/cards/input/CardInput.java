package jokatu.game.games.cards.input;

import jokatu.game.cards.Card;
import jokatu.game.input.Input;
import org.jetbrains.annotations.NotNull;

public class CardInput implements Input {

	private final Card card;

	public CardInput(@NotNull Card card) {
		this.card = card;
	}

	@NotNull
	public Card getCard() {
		return card;
	}
}
