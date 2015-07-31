package jokatu.components.dao;

import jokatu.game.Game;
import jokatu.game.GameID;
import jokatu.identity.IdentifiableReader;
import jokatu.identity.IdentifiableRegistry;
import ophelia.collections.set.UnmodifiableSet;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Steven Weston
 */
@Component
public class GameDao implements IdentifiableReader<GameID, Game<?, ?, ?>>, IdentifiableRegistry<GameID, Game<?, ?, ?>> {

	private final ConcurrentMap<GameID, Game<?, ?, ?>> games = new ConcurrentHashMap<>();

	public UnmodifiableSet<Game<?, ?, ?>> getAll() {
		return new UnmodifiableSet<>(games.values());
	}

	@Override
	public Game<?, ?, ?> read(GameID gameID) {
		return games.get(gameID);
	}

	@Override
	public void register(Game<?, ?, ?> identifiable) {
		games.put(identifiable.getIdentifier(), identifiable);
	}
}
