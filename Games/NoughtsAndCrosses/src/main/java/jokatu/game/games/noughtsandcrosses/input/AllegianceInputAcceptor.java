package jokatu.game.games.noughtsandcrosses.input;

import jokatu.game.event.StageOverEvent;
import jokatu.game.games.noughtsandcrosses.player.NoughtsAndCrossesPlayer;
import jokatu.game.input.InputAcceptor;
import jokatu.game.status.StandardTextStatus;
import ophelia.collections.set.bounded.BoundedPair;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

import static jokatu.game.games.noughtsandcrosses.input.NoughtOrCross.other;

public class AllegianceInputAcceptor extends InputAcceptor<AllegianceInput, NoughtsAndCrossesPlayer> {


	private final BoundedPair<NoughtsAndCrossesPlayer> players;

	public AllegianceInputAcceptor(Collection<NoughtsAndCrossesPlayer> players, StandardTextStatus status) {
		this.players = new BoundedPair<>(players);

		status.setText(
				"Waiting for either {0} or {1} to choose an allegiance.",
				this.players.getFirst(),
				this.players.getSecond()
		);
	}

	@NotNull
	@Override
	protected Class<AllegianceInput> getInputClass() {
		return AllegianceInput.class;
	}

	@NotNull
	@Override
	protected Class<NoughtsAndCrossesPlayer> getPlayerClass() {
		return NoughtsAndCrossesPlayer.class;
	}

	@Override
	protected void acceptCastInputAndPlayer(@NotNull AllegianceInput input, @NotNull NoughtsAndCrossesPlayer inputter) throws Exception {
		if (inputter.getAllegiance() == null) {
			NoughtOrCross allegiance = input.getNoughtOrCross();
			NoughtsAndCrossesPlayer other = players.getOther(inputter);
			if (other == null) {
				throw new Exception("The other player could not be found.");
			}
			inputter.setAllegiance(allegiance);
			other.setAllegiance(other(allegiance));
			fireEvent(new StageOverEvent());
		} else {
			throw new AllegianceAlreadyChosenException();
		}
	}

	private static class AllegianceAlreadyChosenException extends Exception {
		AllegianceAlreadyChosenException() {
			super("You can't change your allegiance.");
		}
	}
}
