package jokatu.game.games.rockpaperscissors.game;

import jokatu.game.Stage;
import jokatu.game.games.rockpaperscissors.input.RockPaperScissorsInputAcceptor;
import jokatu.game.games.rockpaperscissors.player.RockPaperScissorsPlayer;
import jokatu.game.status.StandardTextStatus;
import ophelia.collections.set.bounded.BoundedPair;

class InputStage extends Stage {

	InputStage(BoundedPair<RockPaperScissorsPlayer> players, StandardTextStatus status) {
		super(new RockPaperScissorsInputAcceptor(players, status));
	}
}
