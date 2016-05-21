package jokatu.game.games.cards.input;

import jokatu.game.games.cards.player.CardPlayer;
import jokatu.game.input.InputAcceptor;
import org.jetbrains.annotations.NotNull;

public class CardInputAcceptor extends InputAcceptor<CardInput, CardPlayer> {
	@NotNull
	@Override
	protected Class<CardInput> getInputClass() {
		return CardInput.class;
	}

	@NotNull
	@Override
	protected Class<CardPlayer> getPlayerClass() {
		return CardPlayer.class;
	}

	@Override
	protected void acceptCastInputAndPlayer(@NotNull CardInput input, @NotNull CardPlayer inputter) throws Exception {

	}
}
