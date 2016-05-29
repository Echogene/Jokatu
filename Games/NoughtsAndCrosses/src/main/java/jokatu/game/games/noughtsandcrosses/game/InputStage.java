package jokatu.game.games.noughtsandcrosses.game;

import jokatu.game.MultiInputStage;
import jokatu.game.games.noughtsandcrosses.input.NoughtsAndCrossesInputAcceptor;
import jokatu.game.games.noughtsandcrosses.player.NoughtsAndCrossesPlayer;
import jokatu.game.status.StandardTextStatus;

import java.util.Map;

class InputStage extends MultiInputStage {

	InputStage(Map<String, NoughtsAndCrossesPlayer> players, StandardTextStatus status) {
		super(new NoughtsAndCrossesInputAcceptor(players.values(), status));
	}
}
