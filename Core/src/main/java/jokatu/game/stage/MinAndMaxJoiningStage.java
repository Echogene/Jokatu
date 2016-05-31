package jokatu.game.stage;

import jokatu.game.MultiInputStage;
import jokatu.game.event.StageOverEvent;
import jokatu.game.joining.JoiningInputAcceptor;
import jokatu.game.joining.PlayerJoinedEvent;
import jokatu.game.joining.finish.FinishJoiningInputAcceptor;
import jokatu.game.player.Player;
import jokatu.game.status.StandardTextStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class MinAndMaxJoiningStage<P extends Player> extends MultiInputStage {

	private final int minimum;

	private final int maximum;

	private final Map<String, P> players;

	private final StandardTextStatus status;

	public MinAndMaxJoiningStage(@NotNull Class<P> playerClass, @NotNull Map<String, P> players, int minimum, int maximum, @NotNull StandardTextStatus status) {
		super();
		this.players = players;
		this.minimum = minimum;
		this.maximum = maximum;
		this.status = status;

		status.setText(
				"Waiting for at least {0} player{1} to join.",
				minimum,
				minimum == 1 ? "" : "s"
		);

		JoiningInputAcceptor<P> joiningInputAcceptor = new JoiningInputAcceptor<>(playerClass, players, minimum);
		joiningInputAcceptor.observe(this::onPlayerJoin);
		addInputAcceptor(joiningInputAcceptor);

		FinishJoiningInputAcceptor<P> finishJoiningInputAcceptor = new FinishJoiningInputAcceptor<>(playerClass, players, minimum);
		addInputAcceptor(finishJoiningInputAcceptor);
	}

	private void onPlayerJoin(@NotNull PlayerJoinedEvent gameEvent) {
		if (players.size() == maximum) {
			fireEvent(new StageOverEvent());
		} else {
			int more = minimum - players.size();
			if (more > 0) {
				status.setText(
						"Waiting for {0} more player{1} to join.",
						more,
						more == 1 ? "" : "s"
				);
			} else {
				more = maximum - players.size();
				status.setText(
						"Waiting for someone to start the game or up to {0} more player{1} to join.",
						more,
						more == 1 ? "" : "s"
				);
			}
		}
	}
}
