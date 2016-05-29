package jokatu.game.games.noughtsandcrosses.game;

import jokatu.game.MultiInputStage;
import jokatu.game.games.noughtsandcrosses.input.AllegianceInputAcceptor;
import jokatu.game.games.noughtsandcrosses.player.NoughtsAndCrossesPlayer;
import jokatu.game.status.StandardTextStatus;

import java.util.Map;

public class AllegianceStage extends MultiInputStage {

	AllegianceStage(Map<String, NoughtsAndCrossesPlayer> players, StandardTextStatus status) {
		super(new AllegianceInputAcceptor(players.values(), status));
	}
}
