package jokatu.game.stage;

import jokatu.game.player.Player;
import jokatu.game.status.StandardTextStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class JoiningStage<P extends Player> extends JoiningInputAcceptor<P> {

	private final StandardTextStatus status;

	public JoiningStage(@NotNull Class<P> playerClass, @NotNull Map<String, P> players, int number, @NotNull StandardTextStatus status) {
		super(playerClass, players, number);

		this.status = status;

		status.setText(
				"Waiting for {0} player{1} to join.",
				number,
				number == 1 ? "" : "s"
		);

		observe(gameEvent -> {
			int more = number - players.size();
			status.setText(
					"Waiting for {0} more player{1} to join.",
					more,
					more == 1 ? "" : "s"
			);
		});
	}
}
