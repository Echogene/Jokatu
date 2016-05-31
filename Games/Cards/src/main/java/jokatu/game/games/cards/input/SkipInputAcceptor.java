package jokatu.game.games.cards.input;

import jokatu.game.event.PublicGameEvent;
import jokatu.game.games.cards.player.CardPlayer;
import jokatu.game.input.InputAcceptor;
import jokatu.game.turn.TurnManager;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;

public class SkipInputAcceptor extends InputAcceptor<SkipInput, CardPlayer, PublicGameEvent> {

	private final TurnManager<CardPlayer> turnManager;

	public SkipInputAcceptor(TurnManager<CardPlayer> turnManager) {
		this.turnManager = turnManager;
	}

	@NotNull
	@Override
	protected Class<SkipInput> getInputClass() {
		return SkipInput.class;
	}

	@NotNull
	@Override
	protected Class<CardPlayer> getPlayerClass() {
		return CardPlayer.class;
	}

	@Override
	protected void acceptCastInputAndPlayer(@NotNull SkipInput input, @NotNull CardPlayer inputter) throws Exception {
		turnManager.assertCurrentPlayer(inputter);

		fireEvent(() -> MessageFormat.format("{0} skipped their turn.", inputter));
		turnManager.next();
	}
}
