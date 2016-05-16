package jokatu.game.games.gameofgames.game;

import jokatu.game.Game;
import jokatu.game.GameID;
import jokatu.game.player.Player;
import jokatu.game.status.Status;
import ophelia.collections.BaseCollection;
import ophelia.collections.set.EmptySet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The game of games.  The beginning.  The end.
 *
 * A game you play to create games.
 */
public class GameOfGames extends Game<Player> {

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
	public Player getPlayerByName(@NotNull String name) {
		return null;
	}

	@NotNull
	@Override
	public Status getStatus() {
		return () -> "Create some games!";
	}

	@Override
	protected void advanceStageInner() {
		if (currentStage == null) {
			currentStage = new GameOfGameStage();
		}
	}

	@Override
	public BaseCollection<Player> getPlayers() {
		return EmptySet.emptySet();
	}
}
