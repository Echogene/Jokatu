package jokatu.game.games.gameofgames.game;

import jokatu.game.Game;
import jokatu.game.GameID;
import jokatu.game.event.StatusUpdateEvent;
import jokatu.game.games.gameofgames.player.GameOfGamesPlayer;
import ophelia.collections.BaseCollection;
import ophelia.collections.set.EmptySet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The game of games.  The beginning.  The end.
 *
 * A game you play to create games.
 */
public class GameOfGames extends Game<GameOfGamesPlayer> {

	public static final String GAME_OF_GAMES = "Game of games";

	GameOfGames(GameID gameID) {
		super(gameID);
	}

	@NotNull
	@Override
	public String getGameName() {
		return GAME_OF_GAMES;
	}

	@Nullable
	@Override
	public GameOfGamesPlayer getPlayerByName(@NotNull String name) {
		return null;
	}

	@Override
	protected void advanceStageInner() {
		if (currentStage == null) {
			currentStage = new GameOfGameStage();
			fireEvent((StatusUpdateEvent) () -> () -> "Create some games!");
		}
	}

	@Override
	public BaseCollection<GameOfGamesPlayer> getPlayers() {
		return EmptySet.emptySet();
	}
}
