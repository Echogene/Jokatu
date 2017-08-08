package jokatu.game.input;

import jokatu.game.event.StageOverEvent;
import jokatu.game.input.finishstage.EndStageInput;
import jokatu.game.player.Player;
import ophelia.collections.BaseCollection;
import org.jetbrains.annotations.NotNull;

public class EndStageInputAcceptor<P extends Player> extends AbstractInputAcceptor<EndStageInput, P, StageOverEvent> {

	private final BaseCollection<P> players;
	private final Class<P> playerClass;

	public EndStageInputAcceptor(@NotNull Class<P> playerClass, @NotNull BaseCollection<P> players) {
		this.players = players;
		this.playerClass = playerClass;
	}

	@NotNull
	@Override
	protected Class<EndStageInput> getInputClass() {
		return EndStageInput.class;
	}

	@NotNull
	@Override
	protected Class<P> getPlayerClass() {
		return playerClass;
	}

	@Override
	protected void acceptCastInputAndPlayer(@NotNull EndStageInput input, @NotNull P inputter) throws Exception {
		if (!players.contains(inputter)) {
			throw new UnacceptableInputException("You can't end the stage if you're not playing!");
		}
		fireEvent(new StageOverEvent());
	}
}
