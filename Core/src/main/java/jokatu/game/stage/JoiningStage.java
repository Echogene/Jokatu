package jokatu.game.stage;

import jokatu.game.player.Player;
import jokatu.game.status.StandardTextStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class JoiningStage<P extends Player> extends JoiningInputAcceptor<P> {

	public JoiningStage(@NotNull Class<P> playerClass, @NotNull Map<String, P> players, int number, @NotNull StandardTextStatus status) {
		super(playerClass, players, number, status);
	}
}
