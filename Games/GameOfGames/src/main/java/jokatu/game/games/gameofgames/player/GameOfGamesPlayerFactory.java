package jokatu.game.games.gameofgames.player;

import jokatu.game.games.gameofgames.game.GameOfGames;
import jokatu.game.player.PlayerFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class GameOfGamesPlayerFactory extends PlayerFactory<GameOfGamesPlayer, GameOfGames> {
	@NotNull
	@Override
	protected Class<GameOfGames> getGameClass() {
		return GameOfGames.class;
	}

	@NotNull
	@Override
	protected GameOfGamesPlayer produceInCastGame(@NotNull GameOfGames game, @NotNull String username) {
		return new GameOfGamesPlayer(username);
	}
}
