package jokatu.game.stage;

import jokatu.game.Stage;
import jokatu.game.event.GameEvent;
import jokatu.game.event.StageOverEvent;
import jokatu.game.input.Input;
import jokatu.game.joining.JoiningInputAcceptor;
import jokatu.game.joining.PlayerJoinedEvent;
import jokatu.game.player.Player;
import jokatu.game.status.StandardTextStatus;
import ophelia.event.observable.AbstractSynchronousObservable;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class JoiningStage<P extends Player> extends AbstractSynchronousObservable<GameEvent> implements Stage<GameEvent> {

	private final JoiningInputAcceptor<P> inputAcceptor;

	private final int number;

	private final Map<String, P> players;

	private final StandardTextStatus status;

	public JoiningStage(@NotNull Class<P> playerClass, @NotNull Map<String, P> players, int number, @NotNull StandardTextStatus status) {
		inputAcceptor = new JoiningInputAcceptor<>(playerClass, players, number);

		this.players = players;
		this.number = number;
		this.status = status;

		status.setText(
				"Waiting for {0} player{1} to join.",
				number,
				number == 1 ? "" : "s"
		);

		inputAcceptor.observe(this::onPlayerJoin);

		// Forward the events.
		inputAcceptor.observe(this::fireEvent);
	}

	private void onPlayerJoin(PlayerJoinedEvent gameEvent) {
		if (players.size() == number) {
			fireEvent(new StageOverEvent());
		} else {
			int more = number - players.size();
			status.setText(
					"Waiting for {0} more player{1} to join.",
					more,
					more == 1 ? "" : "s"
			);
		}
	}

	@Override
	public void accept(@NotNull Input input, @NotNull Player player) throws Exception {
		inputAcceptor.accept(input, player);
	}
}
