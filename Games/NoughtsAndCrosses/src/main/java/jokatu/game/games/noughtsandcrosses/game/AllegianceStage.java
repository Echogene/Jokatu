package jokatu.game.games.noughtsandcrosses.game;

import jokatu.game.Stage;
import jokatu.game.games.noughtsandcrosses.input.AllegianceInputAcceptor;
import jokatu.game.games.noughtsandcrosses.player.NoughtsAndCrossesPlayer;
import jokatu.game.status.StandardTextStatus;

import java.util.Map;

class AllegianceStage extends Stage {

	AllegianceStage(Map<String, NoughtsAndCrossesPlayer> players, StandardTextStatus status) {
		super(new AllegianceInputAcceptor(players.values(), status));
	}
}
