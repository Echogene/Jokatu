package jokatu.game.games.rockpaperscissors.game;

import jokatu.game.MultiInputStage;
import jokatu.game.games.rockpaperscissors.input.RockPaperScissorsInputAcceptor;
import jokatu.game.player.StandardPlayer;
import jokatu.game.status.StandardTextStatus;

import java.util.Map;

class InputStage extends MultiInputStage {

	InputStage(Map<String, StandardPlayer> players, StandardTextStatus status) {
		super(new RockPaperScissorsInputAcceptor(players.values(), status));
	}
}
