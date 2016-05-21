package jokatu.game.games.cards.game;

import jokatu.game.Stage;
import jokatu.game.games.cards.input.CardInputAcceptor;

class CardStage extends Stage {

	CardStage() {
		super(new CardInputAcceptor());
	}
}
