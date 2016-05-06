package jokatu.game.games.noughtsandcrosses.player;

import jokatu.game.games.noughtsandcrosses.input.NoughtOrCross;
import jokatu.game.player.AbstractPlayer;

public class NoughtsAndCrossesPlayer extends AbstractPlayer {

	private NoughtOrCross allegiance;

	public NoughtsAndCrossesPlayer(String username) {
		super(username);
	}

	public NoughtOrCross getAllegiance() {
		return allegiance;
	}
}
