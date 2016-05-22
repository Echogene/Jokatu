package jokatu.game.games.cards.game;

import jokatu.game.Stage;
import jokatu.game.games.cards.input.CardInputAcceptor;
import jokatu.game.games.cards.player.CardPlayer;
import jokatu.game.status.StandardTextStatus;

import java.util.Map;

class CardStage extends Stage {

	CardStage(Map<String, CardPlayer> players, StandardTextStatus status) {
		super(new CardInputAcceptor(players, status));
	}
}
