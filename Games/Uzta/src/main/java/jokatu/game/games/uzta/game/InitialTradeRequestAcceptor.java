package jokatu.game.games.uzta.game;

import jokatu.game.games.uzta.graph.NodeType;
import jokatu.game.games.uzta.input.FullPlayerTradeRequest;
import jokatu.game.games.uzta.input.InitialTradeRequest;
import jokatu.game.games.uzta.player.UztaPlayer;
import jokatu.game.input.AnyEventInputAcceptor;
import jokatu.game.input.UnacceptableInputException;
import jokatu.ui.DialogFormBuilder;
import jokatu.ui.Form;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static jokatu.game.event.DialogRequest.requestDialogFor;
import static jokatu.ui.Form.FormFieldType.RANGE;
import static org.springframework.util.StringUtils.capitalize;

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
	protected void acceptCastInputAndPlayer(@NotNull InitialTradeRequest input, @NotNull UztaPlayer inputter) throws
			Exception {
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
			List<Form.FormField<?>> fields = Arrays.stream(NodeType.values())
					.map(type -> new Form.FormField<>(
							type.toString(),
							capitalize(type.getPlural()),
							RANGE,
							input.getResource() == type ? 1 : 0
					))
					.collect(Collectors.toList());
			fireEvent(
					requestDialogFor(
							FullPlayerTradeRequest.class,
							inputter,
							"Request trade",
							"You can modify your trade before submitting."
					)
							.withForm(
									new DialogFormBuilder()
											.withFields(fields)
											.build()
							)
							.then(fullRequest -> {
								// todo:
							})
			);
		}
	}
}
