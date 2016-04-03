package jokatu.game.games.rockpaperscissors.game;

import jokatu.game.Stage;
import jokatu.game.games.rockpaperscissors.player.RockPaperScissorsPlayer;
import jokatu.game.joining.JoinInputAcceptor;
import jokatu.game.status.StandardTextStatus;
import ophelia.collections.set.bounded.BoundedPair;

class JoiningStage extends Stage {

	JoiningStage(BoundedPair<RockPaperScissorsPlayer> players, StandardTextStatus status) {
		super(new JoinInputAcceptor<>(RockPaperScissorsPlayer.class, players, status));
	}
}
