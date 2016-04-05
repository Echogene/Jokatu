package jokatu.game.games.noughtsandcrosses.input;

import jokatu.game.games.noughtsandcrosses.event.CellChosenEvent;
import jokatu.game.games.noughtsandcrosses.player.NoughtsAndCrossesPlayer;
import jokatu.game.input.InputAcceptor;
import jokatu.game.status.StandardTextStatus;
import ophelia.collections.set.bounded.BoundedPair;

public class NoughtsAndCrossesInputAcceptor extends InputAcceptor<NoughtsAndCrossesInput, NoughtsAndCrossesPlayer> {

	public NoughtsAndCrossesInputAcceptor(BoundedPair<NoughtsAndCrossesPlayer> players, StandardTextStatus status) {
		super();
	}

	@Override
	protected Class<NoughtsAndCrossesInput> getInputClass() {
		return NoughtsAndCrossesInput.class;
	}

	@Override
	protected Class<NoughtsAndCrossesPlayer> getPlayerClass() {
		return NoughtsAndCrossesPlayer.class;
	}

	@Override
	protected void acceptCastInputAndPlayer(NoughtsAndCrossesInput input, NoughtsAndCrossesPlayer inputter) throws Exception {
		fireEvent(new CellChosenEvent(input.getCellId()));
	}
}
