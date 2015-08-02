package jokatu.game.factory;

import jokatu.game.Game;
import jokatu.game.GameID;
import jokatu.identity.Identifier;

import java.util.concurrent.atomic.AtomicLong;

public abstract class AbstractGameFactory<G extends Game<?, ?, ?, ?>> implements GameFactory<G> {

	private static final Identifier<GameID> GAME_IDENTIFIER = new Identifier<GameID>() {

		private final AtomicLong id = new AtomicLong(0);

		@Override
		public GameID get() {
			return new GameID(id.getAndIncrement());
		}
	};

	@Override
	public G produce() {
		return produce(GAME_IDENTIFIER.get());
	}

	protected abstract G produce(GameID gameID);
}
