package jokatu.game.games.uzta.game;

import jokatu.game.games.uzta.input.FullPlayerTradeRequest;
import jokatu.game.games.uzta.input.InitialTradeRequest;
import jokatu.game.games.uzta.player.UztaPlayer;
import jokatu.game.input.AnyEventInputAcceptor;
import jokatu.game.input.UnacceptableInputException;
import jokatu.ui.DialogFormBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

import static jokatu.game.event.DialogRequest.requestDialogFor;
import static jokatu.game.games.uzta.graph.NodeType.CIRCLE;
import static jokatu.game.games.uzta.graph.NodeType.RHOMBUS;
import static jokatu.game.games.uzta.graph.NodeType.SQUARE;
import static jokatu.ui.Form.FormFieldType.RANGE;

public class InitialTradeRequestAcceptor extends AnyEventInputAcceptor<InitialTradeRequest, UztaPlayer> {
	private final Map<String, UztaPlayer> players;

	InitialTradeRequestAcceptor(Map<String, UztaPlayer> players) {

		this.players = players;
	}

	@NotNull
	@Override
	protected Class<InitialTradeRequest> getInputClass() {
		return InitialTradeRequest.class;
	}

	@NotNull
	@Override
	protected Class<UztaPlayer> getPlayerClass() {
		return UztaPlayer.class;
	}

	@Override
	protected void acceptCastInputAndPlayer(@NotNull InitialTradeRequest input, @NotNull UztaPlayer inputter) throws Exception {
		@Nullable String playerName = input.getPlayerName();
		if (playerName == null) {

		} else {
			UztaPlayer requestedPlayer = players.get(playerName);
			if (requestedPlayer == null) {
				throw new UnacceptableInputException(
						"You can''t request at trade from {0}; they are not playing the game!", playerName
				);
			}
			if (requestedPlayer == inputter) {
				throw new UnacceptableInputException("You can't trade with yourself!");
			}
			fireEvent(
					requestDialogFor(
							FullPlayerTradeRequest.class,
							inputter,
							"Request trade",
							"You can modify your trade before submitting."
					)
							.withForm(
									new DialogFormBuilder()
											.withField(RHOMBUS.toString(), RHOMBUS.getPlural(), RANGE, 1)
											.withField(CIRCLE.toString(), CIRCLE.getPlural(), RANGE, 1)
											.withField(SQUARE.toString(), SQUARE.getPlural(), RANGE, 1)
											.build()
							)
							.then(fullRequest -> {
								// todo:
							})
			);
		}
	}
}
