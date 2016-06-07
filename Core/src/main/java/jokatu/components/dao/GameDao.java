package jokatu.components.dao;

import jokatu.game.Game;
import jokatu.game.GameID;
import jokatu.game.player.Player;
import jokatu.identity.IdentifiableDao;
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
public class GameDao implements IdentifiableDao<GameID, Game<? extends Player>> {

	private final ConcurrentMap<GameID, Game<? extends Player>> games = new ConcurrentHashMap<>();

	@Nullable
	@Override
	public Game<? extends Player> read(@NotNull GameID identity) {
		return games.get(identity);
	}

	@Override
	public void register(@NotNull Game<? extends Player> identifiable) {
		games.put(identifiable.getIdentifier(), identifiable);
	}
}
