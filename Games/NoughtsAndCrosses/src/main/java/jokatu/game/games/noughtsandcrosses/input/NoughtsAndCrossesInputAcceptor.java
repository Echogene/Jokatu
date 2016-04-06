package jokatu.game.games.noughtsandcrosses.input;

import jokatu.game.games.noughtsandcrosses.event.CellChosenEvent;
import jokatu.game.games.noughtsandcrosses.player.NoughtsAndCrossesPlayer;
import jokatu.game.input.InputAcceptor;
import jokatu.game.status.StandardTextStatus;
import ophelia.collections.set.bounded.BoundedPair;

import java.util.HashMap;
import java.util.Map;

import static jokatu.game.games.noughtsandcrosses.input.NoughtOrCross.CROSS;
import static jokatu.game.games.noughtsandcrosses.input.NoughtOrCross.other;

public class NoughtsAndCrossesInputAcceptor extends InputAcceptor<NoughtsAndCrossesInput, NoughtsAndCrossesPlayer> {

	private final Map<Integer, NoughtOrCross> inputs = new HashMap<>();

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
		Integer cell = input.getCellId();
		if (inputs.containsKey(cell)) {
			inputs.put(cell, other(inputs.get(cell)));
		} else {
			inputs.put(cell, CROSS);
		}
		fireEvent(new CellChosenEvent(cell, inputs.get(cell)));
	}
}
