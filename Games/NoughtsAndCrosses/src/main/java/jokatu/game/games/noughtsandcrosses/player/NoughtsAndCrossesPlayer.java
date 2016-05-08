package jokatu.game.games.noughtsandcrosses.player;

import jokatu.game.games.noughtsandcrosses.input.NoughtOrCross;
import jokatu.game.player.AbstractPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NoughtsAndCrossesPlayer extends AbstractPlayer {

	private NoughtOrCross allegiance;

	NoughtsAndCrossesPlayer(String username) {
		super(username);
	}

	@Nullable
	public NoughtOrCross getAllegiance() {
		return allegiance;
	}

	public void setAllegiance(@NotNull NoughtOrCross noughtOrCross) {
		allegiance = noughtOrCross;
	}
}
