package jokatu.game.games.gameofgames.game;

import jokatu.game.AbstractGameFactory;
import jokatu.game.GameID;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class GameOfGamesFactory extends AbstractGameFactory<GameOfGames> {
	@NotNull
	@Override
	protected GameOfGames produce(@NotNull GameID gameID, @NotNull String creatorName) {
		return new GameOfGames(gameID);
	}
}
