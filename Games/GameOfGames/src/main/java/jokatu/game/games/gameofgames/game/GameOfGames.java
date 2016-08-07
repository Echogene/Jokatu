package jokatu.game.games.gameofgames.game;

import jokatu.game.Game;
import jokatu.game.GameID;
import jokatu.game.player.StandardPlayer;
import jokatu.game.stage.machine.SingleStageMachine;
import org.jetbrains.annotations.NotNull;

/**
 * The game of games.  The beginning.  The end.
 *
 * A game you play to create games.
 */
public class GameOfGames extends Game<StandardPlayer> {

	public static final String GAME_OF_GAMES = "Game of games";

	GameOfGames(GameID gameID) {
		super(gameID);

		stageMachine = new SingleStageMachine(new GameOfGameStage());
	}

	@NotNull
	@Override
	public String getGameName() {
		return GAME_OF_GAMES;
	}
}
