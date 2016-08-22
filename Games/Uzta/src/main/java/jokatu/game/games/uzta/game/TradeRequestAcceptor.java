package jokatu.game.games.uzta.game;

import jokatu.game.games.uzta.input.TradeRequest;
import jokatu.game.games.uzta.player.UztaPlayer;
import jokatu.game.input.AnyEventInputAcceptor;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;

public class TradeRequestAcceptor extends AnyEventInputAcceptor<TradeRequest, UztaPlayer> {
	@NotNull
	@Override
	protected Class<TradeRequest> getInputClass() {
		return TradeRequest.class;
	}

	@NotNull
	@Override
	protected Class<UztaPlayer> getPlayerClass() {
		return UztaPlayer.class;
	}

	@Override
	protected void acceptCastInputAndPlayer(@NotNull TradeRequest input, @NotNull UztaPlayer inputter) throws Exception {
		System.out.println(MessageFormat.format("{0} wants to get some {1} from {2}",
				inputter.getName(),
				input.getResource().getPlural(),
				input.getPlayerName()
		));
	}
}
