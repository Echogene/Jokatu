package jokatu.game.games.gameofgames.player;

import jokatu.game.player.PlayerFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class GameOfGamesPlayerFactory implements PlayerFactory<GameOfGamesPlayer> {
	@NotNull
	@Override
	public GameOfGamesPlayer produce(@NotNull String username) {
		return new GameOfGamesPlayer(username);
	}
}