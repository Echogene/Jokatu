package jokatu.game.joining.finish;

import jokatu.game.event.StageOverEvent;
import jokatu.game.input.InputAcceptor;
import jokatu.game.input.UnacceptableInputException;
import jokatu.game.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class FinishJoiningInputAcceptor<P extends Player> extends InputAcceptor<FinishJoiningInput, P, StageOverEvent> {

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
	protected Class<FinishJoiningInput> getInputClass() {
		return FinishJoiningInput.class;
	}

	@NotNull
	@Override
	protected Class<P> getPlayerClass() {
		return playerClass;
	}

	@Override
	protected void acceptCastInputAndPlayer(@NotNull FinishJoiningInput input, @NotNull P inputter) throws Exception {
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
