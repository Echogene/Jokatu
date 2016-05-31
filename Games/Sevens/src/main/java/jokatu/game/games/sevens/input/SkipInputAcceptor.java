package jokatu.game.games.sevens.input;

import jokatu.game.event.PublicGameEvent;
import jokatu.game.games.sevens.player.SevensPlayer;
import jokatu.game.input.InputAcceptor;
import jokatu.game.turn.TurnManager;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;

public class SkipInputAcceptor extends InputAcceptor<SkipInput, SevensPlayer, PublicGameEvent> {

	private final TurnManager<SevensPlayer> turnManager;

	public SkipInputAcceptor(TurnManager<SevensPlayer> turnManager) {
		this.turnManager = turnManager;
	}

	@NotNull
	@Override
	protected Class<SkipInput> getInputClass() {
		return SkipInput.class;
	}

	@NotNull
	@Override
	protected Class<SevensPlayer> getPlayerClass() {
		return SevensPlayer.class;
	}

	@Override
	protected void acceptCastInputAndPlayer(@NotNull SkipInput input, @NotNull SevensPlayer inputter) throws Exception {
		turnManager.assertCurrentPlayer(inputter);

		fireEvent(() -> MessageFormat.format("{0} skipped their turn.", inputter));
		turnManager.next();
	}
}
