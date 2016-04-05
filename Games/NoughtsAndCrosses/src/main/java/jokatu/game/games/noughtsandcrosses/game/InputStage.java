package jokatu.game.games.noughtsandcrosses.game;

import jokatu.game.Stage;
import jokatu.game.games.noughtsandcrosses.input.NoughtsAndCrossesInputAcceptor;
import jokatu.game.games.noughtsandcrosses.player.NoughtsAndCrossesPlayer;
import jokatu.game.status.StandardTextStatus;
import ophelia.collections.set.bounded.BoundedPair;

class InputStage extends Stage {

	InputStage(BoundedPair<NoughtsAndCrossesPlayer> players, StandardTextStatus status) {
		super(new NoughtsAndCrossesInputAcceptor(players, status));
	}
}
