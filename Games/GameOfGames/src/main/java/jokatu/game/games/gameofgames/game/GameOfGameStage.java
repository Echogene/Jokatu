package jokatu.game.games.gameofgames.game;

import jokatu.game.games.gameofgames.event.GameCreatedEvent;
import jokatu.game.games.gameofgames.input.CreateGameInput;
import jokatu.game.input.AbstractInputAcceptor;
import jokatu.game.player.StandardPlayer;
import org.jetbrains.annotations.NotNull;

class GameOfGameStage extends AbstractInputAcceptor<CreateGameInput, StandardPlayer, GameCreatedEvent> {
	@NotNull
	@Override
	protected Class<CreateGameInput> getInputClass() {
		return CreateGameInput.class;
	}

	@NotNull
	@Override
	protected Class<StandardPlayer> getPlayerClass() {
		return StandardPlayer.class;
	}

	@Override
	protected void acceptCastInputAndPlayer(@NotNull CreateGameInput input, @NotNull StandardPlayer inputter) throws Exception {
		String gameName = input.getGameName();
		fireEvent(new GameCreatedEvent(inputter, gameName));
	}
}
