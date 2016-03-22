package jokatu.components.dao;

import jokatu.game.Game;
import jokatu.game.GameID;
import jokatu.game.input.Input;
import jokatu.game.player.Player;
import jokatu.identity.IdentifiableDao;
import ophelia.collections.set.UnmodifiableSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * A dao that grants access to all games currently registered by referring to the game's ID.
 * @author Steven Weston
 */
@Component
public class GameDao implements IdentifiableDao<GameID, Game<Player>> {

	private final ConcurrentMap<GameID, Game<Player>> games = new ConcurrentHashMap<>();

	public UnmodifiableSet<Game<Player>> getAll() {
		return new UnmodifiableSet<>(games.values());
	}

	@Nullable
	@Override
	public Game<Player> read(@NotNull GameID identity) {
		return games.get(identity);
	}

	@Override
	public void register(@NotNull Game<Player> identifiable) {
		games.put(identifiable.getIdentifier(), identifiable);
	}
}
