package jokatu.game.stage;

import jokatu.game.MultiInputStage;
import jokatu.game.joining.JoinInputAcceptor;
import jokatu.game.player.Player;
import jokatu.game.status.StandardTextStatus;

import java.util.Map;

public class JoiningStage<P extends Player> extends MultiInputStage {

	public JoiningStage(Class<P> playerClass, Map<String, P> players, int limit, StandardTextStatus status) {
		super(new JoinInputAcceptor<>(playerClass, players, limit, status));
	}
}
