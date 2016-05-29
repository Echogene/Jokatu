package jokatu.game.games.cards.game;

import jokatu.game.MultiInputStage;
import jokatu.game.cards.Card;
import jokatu.game.cards.Suit;
import jokatu.game.games.cards.input.CardInputAcceptor;
import jokatu.game.games.cards.player.CardPlayer;
import jokatu.game.status.StandardTextStatus;

import java.util.Map;
import java.util.TreeSet;

class CardStage extends MultiInputStage {

	CardStage(Map<String, CardPlayer> players, StandardTextStatus status, Map<Suit, TreeSet<Card>> playedCards) {
		super(new CardInputAcceptor(players, status, playedCards));
	}
}
