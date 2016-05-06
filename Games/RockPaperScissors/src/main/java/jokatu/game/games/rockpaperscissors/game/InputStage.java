package jokatu.game.games.rockpaperscissors.game;

import jokatu.game.Stage;
import jokatu.game.games.rockpaperscissors.input.RockPaperScissorsInputAcceptor;
import jokatu.game.games.rockpaperscissors.player.RockPaperScissorsPlayer;
import jokatu.game.status.StandardTextStatus;

import java.util.Map;

class InputStage extends Stage {

	InputStage(Map<String, RockPaperScissorsPlayer> players, StandardTextStatus status) {
		super(new RockPaperScissorsInputAcceptor(players.values(), status));
	}
}
