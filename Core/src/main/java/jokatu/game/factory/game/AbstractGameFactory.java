package jokatu.game.factory.game;

import jokatu.components.dao.GameDao;
import jokatu.game.Game;
import jokatu.game.GameID;
import jokatu.game.input.Input;
import jokatu.game.player.Player;
import jokatu.identity.Identifier;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.atomic.AtomicLong;

/**
 * An abstract version of game factory that stores a common identifier to give IDs to games in subclasses.
 */
public abstract class AbstractGameFactory implements GameFactory {

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
	public final Game<? extends Player, ? extends Input> produceGame(@NotNull String creatorName) {
		Game<? extends Player, ? extends Input> game = produce(GAME_IDENTIFIER.get(), creatorName);
		gameDao.register((Game<Player, Input>) game);
		return game;
	}

	@NotNull
	protected abstract Game<? extends Player, ? extends Input> produce(@NotNull GameID gameID, @NotNull String creatorName);
}
