package jokatu.game.input;

import jokatu.game.user.player.Player;

public abstract class AbstractInput<P extends Player> implements Input<P> {

	private final P player;

	protected AbstractInput(P player) {
		this.player = player;
	}

	@Override
	public P getPlayer() {
		return player;
	}
}
