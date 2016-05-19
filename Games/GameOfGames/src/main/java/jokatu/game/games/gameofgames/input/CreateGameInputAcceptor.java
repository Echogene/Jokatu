package jokatu.game.games.gameofgames.input;

import jokatu.game.games.gameofgames.event.GameCreatedEvent;
import jokatu.game.games.gameofgames.player.GameOfGamesPlayer;
import jokatu.game.input.InputAcceptor;
import org.jetbrains.annotations.NotNull;

public class CreateGameInputAcceptor extends InputAcceptor<CreateGameInput, GameOfGamesPlayer> {
	@NotNull
	@Override
	protected Class<CreateGameInput> getInputClass() {
		return CreateGameInput.class;
	}

	@NotNull
	@Override
	protected Class<GameOfGamesPlayer> getPlayerClass() {
		return GameOfGamesPlayer.class;
	}

	@Override
	protected void acceptCastInputAndPlayer(@NotNull CreateGameInput input, @NotNull GameOfGamesPlayer inputter) throws Exception {
		String gameName = input.getGameName();
		fireEvent(new GameCreatedEvent(inputter, gameName));
	}
}
