package jokatu.components.dao;

import jokatu.game.Game;
import jokatu.game.GameID;
import jokatu.game.input.Input;
import jokatu.game.user.player.Player;
import jokatu.identity.IdentifiableReader;
import jokatu.identity.IdentifiableRegistry;
import ophelia.collections.BaseCollection;
import ophelia.collections.set.UnmodifiableSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Steven Weston
 */
@Component
public class GameDao implements IdentifiableReader<GameID, Game<?, ?, ?, ?>>, IdentifiableRegistry<GameID, Game<?, ?, ?, ?>> {

	private final ConcurrentMap<GameID, Game<?, ?, ?, ?>> games = new ConcurrentHashMap<>();

	public UnmodifiableSet<Game<?, ?, ?, ?>> getAll() {
		return new UnmodifiableSet<>(games.values());
	}

	@Nullable
	@Override
	public Game<?, ?, ?, ?> read(@NotNull GameID gameID) {
		return games.get(gameID);
	}

	@Nullable
	// I hate Java generics so much.  If we give this the right name, it apparently doesn't override the interface
	// method even though it has the same erasure as it.
	public <P extends Player, I extends Input<P>, C extends BaseCollection<P>, E> Game<P, I, C, E> uncheckedRead(GameID gameID) {
		return (Game<P, I, C, E>) read(gameID);
	}

	@Override
	public void register(@NotNull Game<?, ?, ?, ?> identifiable) {
		games.put(identifiable.getIdentifier(), identifiable);
	}
}
