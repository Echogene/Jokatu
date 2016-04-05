package jokatu.game;

import jokatu.game.joining.JoinInputAcceptor;
import jokatu.game.player.Player;
import jokatu.game.status.StandardTextStatus;
import ophelia.collections.set.bounded.BoundedPair;

public class JoiningStage<P extends Player> extends Stage {

	public JoiningStage(Class<P> playerClass, BoundedPair<P> players, StandardTextStatus status) {
		super(new JoinInputAcceptor<>(playerClass, players, status));
	}
}
