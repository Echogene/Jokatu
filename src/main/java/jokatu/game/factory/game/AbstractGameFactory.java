package jokatu.game.factory.game;

import jokatu.components.dao.GameDao;
import jokatu.game.Game;
import jokatu.game.GameID;
import jokatu.identity.Identifier;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.atomic.AtomicLong;

public abstract class AbstractGameFactory<G extends Game<?, ?>> implements GameFactory<G> {

	private static final Identifier<GameID> GAME_IDENTIFIER = new Identifier<GameID>() {

		private final AtomicLong id = new AtomicLong(0);

		@Override
		public GameID get() {
			return new GameID(id.getAndIncrement());
		}
	};

	@Autowired
	private GameDao gameDao;

	@NotNull
	@Override
	public G produce() {
		G game = produce(GAME_IDENTIFIER.get());
		gameDao.register(game);
		return game;
	}

	@NotNull
	protected abstract G produce(GameID gameID);
}
