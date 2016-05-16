package jokatu.game.games.gameofgames.input;

import jokatu.game.games.gameofgames.event.GameCreatedEvent;
import jokatu.game.games.gameofgames.player.GameOfGamesPlayer;
import jokatu.game.input.InputAcceptor;

public class CreateGameInputAcceptor extends InputAcceptor<CreateGameInput, GameOfGamesPlayer> {
	@Override
	protected Class<CreateGameInput> getInputClass() {
		return CreateGameInput.class;
	}

	@Override
	protected Class<GameOfGamesPlayer> getPlayerClass() {
		return GameOfGamesPlayer.class;
	}

	@Override
	protected void acceptCastInputAndPlayer(CreateGameInput input, GameOfGamesPlayer inputter) throws Exception {
		String gameName = input.getGameName();
		fireEvent(new GameCreatedEvent(inputter, gameName));
	}
}
