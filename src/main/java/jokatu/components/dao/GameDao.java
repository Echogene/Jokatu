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
 * @author Steven Weston
 */
@Component
public class GameDao implements IdentifiableDao<GameID, Game<Player, Input>> {

	private final ConcurrentMap<GameID, Game<Player, Input>> games = new ConcurrentHashMap<>();

	public UnmodifiableSet<Game<Player, Input>> getAll() {
		return new UnmodifiableSet<>(games.values());
	}

	@Nullable
	@Override
	public Game<Player, Input> read(@NotNull GameID identity) {
		return games.get(identity);
	}

	@Override
	public void register(@NotNull Game<Player, Input> identifiable) {
		games.put(identifiable.getIdentifier(), identifiable);
	}
}
