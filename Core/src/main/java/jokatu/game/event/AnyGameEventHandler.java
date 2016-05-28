package jokatu.game.event;

import jokatu.game.Game;
import org.jetbrains.annotations.NotNull;

public abstract class AnyGameEventHandler<E extends GameEvent> extends EventHandler<Game, E> {

	@NotNull
	@Override
	protected Class<Game> getGameClass() {
		return Game.class;
	}
}
