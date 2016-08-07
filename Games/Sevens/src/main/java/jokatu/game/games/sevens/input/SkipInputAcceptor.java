package jokatu.game.games.sevens.input;

import jokatu.game.event.PublicGameEvent;
import jokatu.game.games.sevens.player.SevensPlayer;
import jokatu.game.input.endturn.EndTurnInputAcceptor;
import jokatu.game.turn.TurnManager;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;

public class SkipInputAcceptor extends EndTurnInputAcceptor<SevensPlayer> {

	public SkipInputAcceptor(@NotNull TurnManager<SevensPlayer> turnManager) {
		super(turnManager, SevensPlayer.class);
	}

	@Override
	protected void fireAdditionalEvents(@NotNull SevensPlayer inputter) {
		fireEvent((PublicGameEvent) () -> MessageFormat.format("{0} skipped their turn.", inputter));
	}
}
