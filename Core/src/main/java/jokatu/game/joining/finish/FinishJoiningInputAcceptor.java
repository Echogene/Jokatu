package jokatu.game.joining.finish;

import jokatu.game.event.StageOverEvent;
import jokatu.game.input.AbstractInputAcceptor;
import jokatu.game.input.UnacceptableInputException;
import jokatu.game.input.finishstage.EndStageInput;
import jokatu.game.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Accepts {@link EndStageInput}s and fires a {@link StageOverEvent} if the input is valid.
 * @param <P> the type of {@link Player} that should be accepted
 */
public class FinishJoiningInputAcceptor<P extends Player> extends AbstractInputAcceptor<EndStageInput, P, StageOverEvent> {

	private final Class<P> playerClass;

	private final Map<String, P> players;

	private final int minimum;

	public FinishJoiningInputAcceptor(@NotNull Class<P> playerClass, @NotNull Map<String, P> players, int minimum) {
		this.playerClass = playerClass;
		this.players = players;
		this.minimum = minimum;
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
		int more = minimum - players.size();
		if (more > 0) {
			throw new UnacceptableInputException(
					"There need{0} to be at least {1} more player{2} before you can start the game.",
					more == 1 ? "s" : "",
					more,
					more == 1 ? "" : "s"
			);
		}
		fireEvent(new StageOverEvent());
	}
}
