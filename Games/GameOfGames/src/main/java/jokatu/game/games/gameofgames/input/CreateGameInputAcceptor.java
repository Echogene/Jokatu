package jokatu.game.games.gameofgames.input;

import jokatu.game.games.gameofgames.event.GameCreatedEvent;
import jokatu.game.input.InputAcceptor;
import jokatu.game.player.StandardPlayer;
import org.jetbrains.annotations.NotNull;

public class CreateGameInputAcceptor extends InputAcceptor<CreateGameInput, StandardPlayer> {
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
