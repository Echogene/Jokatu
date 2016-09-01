package jokatu.game.games.uzta.game;

import jokatu.game.event.dialog.DialogRequest;
import jokatu.game.event.dialog.DialogRequestBuilder;
import jokatu.game.games.uzta.graph.NodeType;
import jokatu.game.games.uzta.input.FullPlayerTradeRequest;
import jokatu.game.games.uzta.input.InitialTradeRequest;
import jokatu.game.games.uzta.player.UztaPlayer;
import jokatu.game.input.AnyEventInputAcceptor;
import jokatu.game.input.UnacceptableInputException;
import jokatu.game.input.acknowledge.AcknowledgeInput;
import jokatu.game.player.Player;
import jokatu.ui.*;
import jokatu.ui.FormSelect.Option;
import ophelia.collections.bag.BaseIntegerBag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static jokatu.ui.FormField.FormFieldType.NUMBER;
import static ophelia.util.FunctionUtils.not;
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
		@Nullable
		String playerName = input.getPlayerName();
		if (playerName == null) {

		} else {
			checkPlayerToTradeWith(inputter, playerName);
			Form form = constructFormForTradeConfirmation(inputter, playerName, input.getResource());
			DialogRequest<UztaPlayer, FullPlayerTradeRequest> request = DialogRequestBuilder.forPlayer(inputter)
					.withTitle("Request trade")
					.withMessage("You can modify your trade before submitting.")
					.withInputType(FullPlayerTradeRequest.class)
					.withConsumer(this::acceptFullRequest)
					.withForm(form)
					.build();
			fireEvent(request);
		}
	}

	@NotNull
	private UztaPlayer checkPlayerToTradeWith(@NotNull Player inputter, String playerName) throws
			UnacceptableInputException {
		UztaPlayer requestedPlayer = players.get(playerName);
		if (requestedPlayer == null) {
			throw new UnacceptableInputException(
					"You can''t request at trade from {0}; they are not playing the game!", playerName
			);
		}
		if (requestedPlayer == inputter) {
			throw new UnacceptableInputException("You can't trade with yourself!");
		}
		return requestedPlayer;
	}

	@NotNull
	private Form constructFormForTradeConfirmation(
			@NotNull UztaPlayer inputter,
			@NotNull String playerName,
			@NotNull NodeType resource
	) {
		FormSelect playerField = new FormSelect(
				"player",
				"Player",
				players.keySet().stream()
						.filter(not(inputter.getName()::equals))
						.map(name -> new Option(name, name, playerName.equals(name)))
						.collect(toList())
		);
		List<FormField> wantedFields = Arrays.stream(NodeType.values())
				.map(type -> new FormInput<>(
						type.toString(),
						capitalize(type.getPlural()),
						NUMBER,
						resource == type ? 1 : 0
				))
				.collect(toList());
		List<FormField> givingFields = Arrays.stream(NodeType.values())
				.map(type -> new FormInput<>(
						type.toString() + "_give",
						capitalize(type.getPlural()),
						NUMBER,
						0
				))
				.collect(toList());
		return new DialogFormBuilder()
				.withField(playerField)
				.withDiv("To ask for")
				.withFields(wantedFields)
				.withDiv("To give")
				.withFields(givingFields)
				.build();
	}

	private void acceptFullRequest(
			@NotNull FullPlayerTradeRequest fullPlayerTradeRequest,
			@NotNull UztaPlayer inputter
	) throws UnacceptableInputException {
		@NotNull
		String playerName = fullPlayerTradeRequest.getPlayerName();
		@NotNull
		BaseIntegerBag<NodeType> wantedResources = fullPlayerTradeRequest.getWantedResources();
		@NotNull
		BaseIntegerBag<NodeType> givenResources = fullPlayerTradeRequest.getGivenResources();

		@NotNull
		UztaPlayer playerToTradeWith = checkPlayerToTradeWith(inputter, playerName);


		checkResources(
				wantedResources,
				playerToTradeWith,
				"{0} doesn''t have enough resources to trade {1}.  They still need {2}."
		);

		checkResources(
				givenResources,
				inputter,
				"You don''t have enough resources to give {1}.  You still need {2}."
		);

		DialogRequest<UztaPlayer, AcknowledgeInput> request = DialogRequestBuilder.forPlayer(playerToTradeWith)
				.withTitle(MessageFormat.format("{0} wants to trade with you", inputter.getName()))
				.withMessage(MessageFormat.format(
						"{0} would give you {1} in exchange for {2}",
						inputter.getName(),
						presentResources(givenResources),
						presentResources(wantedResources)
				))
				.withInputType(AcknowledgeInput.class)
				.withConsumer((ack, player) -> {
					if (ack.isAcknowledgement()) {
						// todo: finally do the trade
					}
				})
				.build();

		fireEvent(request);
	}

	private void checkResources(
			@NotNull BaseIntegerBag<NodeType> resources,
			@NotNull UztaPlayer player,
			@NotNull String message
	) throws UnacceptableInputException {
		@NotNull
		BaseIntegerBag<NodeType> resourcesLeftAfterGiving = player.getResourcesLeftAfter(resources);
		if (resourcesLeftAfterGiving.isLacking()) {
			throw new UnacceptableInputException(
					message,
					player.getName(),
					presentResources(resources),
					resourcesLeftAfterGiving.stream()
							.filter(entry -> entry.getRight() < 0)
							.map(entry -> entry.getLeft().getNumber(-entry.getRight()))
							.collect(joining(", "))
			);
		}
	}

	@NotNull
	private String presentResources(@NotNull BaseIntegerBag<NodeType> resources) {
		return resources.stream()
				.map(entry -> entry.getLeft().getNumber(entry.getRight()))
				.collect(joining(", "));
	}
}
