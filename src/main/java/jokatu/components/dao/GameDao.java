package jokatu.components.dao;

import jokatu.game.Game;
import jokatu.game.GameID;
import jokatu.game.event.GameEvent;
import jokatu.game.input.Input;
import jokatu.game.user.player.Player;
import jokatu.identity.IdentifiableDao;
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
public class GameDao implements IdentifiableDao<GameID, Game<?, ?, ?, ?>> {

	private final ConcurrentMap<GameID, Game<?, ?, ?, ?>> games = new ConcurrentHashMap<>();

	public UnmodifiableSet<Game<?, ?, ?, ?>> getAll() {
		return new UnmodifiableSet<>(games.values());
	}

	@Nullable
	@Override
	public Game<?, ?, ?, ?> read(@NotNull GameID identity) {
		return games.get(identity);
	}

	@Nullable
	// I hate Java generics so much.  If we give this the right name, it apparently doesn't override the interface
	// method even though it has the same erasure as it.
	public <P extends Player, I extends Input, C extends BaseCollection<P>, E extends GameEvent<P>> Game<P, I, C, E> uncheckedRead(GameID identity) {
		return (Game<P, I, C, E>) read(identity);
	}

	@Override
	public void register(@NotNull Game<?, ?, ?, ?> identifiable) {
		games.put(identifiable.getIdentifier(), identifiable);
	}
}
