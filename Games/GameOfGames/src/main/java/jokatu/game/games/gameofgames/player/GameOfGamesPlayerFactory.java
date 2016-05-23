package jokatu.game.games.gameofgames.player;

import jokatu.game.Game;
import jokatu.game.player.PlayerFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class GameOfGamesPlayerFactory extends PlayerFactory<GameOfGamesPlayer> {
	@NotNull
	@Override
	public GameOfGamesPlayer produce(@NotNull Game<? extends GameOfGamesPlayer> game, @NotNull String username) {
		return new GameOfGamesPlayer(username);
	}
}
