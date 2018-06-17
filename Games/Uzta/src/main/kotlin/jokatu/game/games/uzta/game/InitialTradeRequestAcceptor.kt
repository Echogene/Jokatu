package jokatu.game.games.uzta.game

import jokatu.game.event.dialog.DialogRequestBuilder
import jokatu.game.games.uzta.graph.NodeType
import jokatu.game.games.uzta.input.FullPlayerTradeRequest
import jokatu.game.games.uzta.input.InitialTradeRequest
import jokatu.game.games.uzta.input.SupplyTradeRequest
import jokatu.game.games.uzta.player.UztaPlayer
import jokatu.game.input.AnyEventInputAcceptor
import jokatu.game.input.UnacceptableInputException
import jokatu.game.input.acknowledge.AcknowledgeInput
import jokatu.game.player.Player
import jokatu.ui.DialogFormBuilder
import jokatu.ui.Form
import jokatu.ui.FormSelect
import jokatu.ui.FormSelect.Option
import jokatu.ui.IntegerField
import ophelia.collections.bag.BagUtils
import ophelia.collections.bag.BaseIntegerBag
import ophelia.exceptions.StackedException
import ophelia.exceptions.voidmaybe.VoidMaybe
import ophelia.exceptions.voidmaybe.VoidMaybe.wrap
import ophelia.exceptions.voidmaybe.VoidMaybeCollectors.merge
import ophelia.function.ExceptionalBiConsumer
import ophelia.util.function.PredicateUtils.not
import org.springframework.util.StringUtils.capitalize
import java.text.MessageFormat
import java.util.Arrays.stream
import java.util.stream.Collectors.joining
import java.util.stream.Collectors.toList

class InitialTradeRequestAcceptor internal constructor(
		private val players: Map<String, UztaPlayer>
) : AnyEventInputAcceptor<InitialTradeRequest, UztaPlayer>(InitialTradeRequest::class, UztaPlayer::class) {

	@Throws(Exception::class)
	override fun acceptCastInputAndPlayer(
			input: InitialTradeRequest,
			inputter: UztaPlayer
	) {
		val playerName = input.playerName
		if (playerName == null) {
			// The player wants to trade with the supply.
			val form = constructFormForSupplyTrade(input.resource)
			val request = DialogRequestBuilder.forPlayer(inputter)
					.withTitle("Trade with the supply")
					.withMessage("Resources can be traded with the supply at a ratio of $SUPPLY_RATIO:1.")
					.withInputType(SupplyTradeRequest::class)
					.withConsumer(ExceptionalBiConsumer<SupplyTradeRequest, UztaPlayer, Exception> { supplyTradeRequest, trader -> this.acceptSupplyRequest(supplyTradeRequest, trader) })
					.withForm(form)
					.build()
			fireEvent(request)
		} else {
			checkPlayerToTradeWith(inputter, playerName)
			val form = constructFormForTradeConfirmation(inputter, playerName, input.resource)
			val request = DialogRequestBuilder.forPlayer(inputter)
					.withTitle("Request trade")
					.withMessage("You can modify your trade before submitting.")
					.withInputType(FullPlayerTradeRequest::class)
					.withConsumer(ExceptionalBiConsumer<FullPlayerTradeRequest, UztaPlayer, Exception> { fullPlayerTradeRequest, inputter -> this.acceptFullRequest(fullPlayerTradeRequest, inputter) })
					.withForm(form)
					.build()
			fireEvent(request)
		}
	}

	@Throws(StackedException::class, UnacceptableInputException::class)
	private fun acceptSupplyRequest(
			supplyTradeRequest: SupplyTradeRequest,
			trader: UztaPlayer
	) {
		val trade = supplyTradeRequest.trade

		val givenResources = trade.lackingItems.inverse
		checkResources(
				givenResources,
				trader,
				"You don''t have enough resources to give {1}.  You still need {2}."
		)
		verifyGivenResourcesForSupplyTrade(givenResources)

		val gainedResources = trade.surplusItems
		if (0 != gainedResources.size() - givenResources.size() / 3) {
			val message = BagUtils.presentBag(
					trade,
					{ obj, number -> obj.getNumber(number) },
					joining(", ", "You can't get ", " "),
					joining(", ", "by giving ", ".")
			)
			throw UnacceptableInputException("The trade did not balance.  $message")
		}
		trader.giveResources(trade)
	}

	@Throws(StackedException::class)
	private fun verifyGivenResourcesForSupplyTrade(
			givenResources: BaseIntegerBag<NodeType>
	) {
		stream(NodeType.values())
				.map<VoidMaybe>(wrap { resource ->
					val number = givenResources.getNumberOf(resource)
					if (0 != number % SUPPLY_RATIO) {
						throw UnacceptableInputException("You can't give ${resource.getNumber(number)}: $number is not "
								+ "a multiple of $SUPPLY_RATIO.")
					}
				})
				.collect<VoidMaybe, Map<Boolean, Set<VoidMaybe>>>(merge())
				.throwOnFailure()
	}

	@Throws(UnacceptableInputException::class)
	private fun checkPlayerToTradeWith(
			inputter: Player,
			playerName: String
	): UztaPlayer {
		val requestedPlayer = players[playerName] ?: throw UnacceptableInputException(
				"You can't request at trade from $playerName; they are not playing the game!"
		)
		if (requestedPlayer == inputter) {
			throw UnacceptableInputException("You can't trade with yourself!")
		}
		return requestedPlayer
	}

	private fun constructFormForSupplyTrade(
			resource: NodeType
	): Form {
		val fields = stream(NodeType.values())
				.map { type ->
					IntegerField(
							type.toString(),
							capitalize(type.plural),
							if (resource == type) 1 else 0,
							"I want",
							"I will give"
					)
				}
				.collect(toList())
		return DialogFormBuilder()
				.withFields(fields)
				.build()
	}

	private fun constructFormForTradeConfirmation(
			inputter: UztaPlayer,
			playerName: String,
			resource: NodeType
	): Form {
		val playerField = FormSelect(
				"player",
				"Player",
				players.keys.stream()
						.filter(not { inputter.name == it })
						.map { name -> Option(name, name, playerName == name) }
						.collect(toList())
		)
		val fields = stream(NodeType.values())
				.map { type ->
					IntegerField(
							type.toString(),
							capitalize(type.plural),
							if (resource == type) 1 else 0,
							"I want",
							"I will give"
					)
				}
				.collect(toList())
		return DialogFormBuilder()
				.withField(playerField)
				.withDiv("To ask for")
				.withFields(fields)
				.build()
	}

	@Throws(UnacceptableInputException::class)
	private fun acceptFullRequest(
			fullPlayerTradeRequest: FullPlayerTradeRequest,
			inputter: UztaPlayer
	) {
		val playerName = fullPlayerTradeRequest.playerName
		val trade = fullPlayerTradeRequest.trade
		val wantedResources = trade.surplusItems
		val givenResources = trade.lackingItems.inverse

		val playerToTradeWith = checkPlayerToTradeWith(inputter, playerName)

		checkResources(
				wantedResources,
				playerToTradeWith,
				"{0} doesn''t have enough resources to trade {1}.  They still need {2}."
		)

		checkResources(
				givenResources,
				inputter,
				"You don''t have enough resources to give {1}.  You still need {2}."
		)

		val request = DialogRequestBuilder.forPlayer(playerToTradeWith)
				.withTitle("${inputter.name} wants to trade with you")
				.withMessage("${inputter.name} would give you ${presentResources(givenResources)} in exchange for "
						+ "${presentResources(wantedResources)}.")
				.withInputType(AcknowledgeInput::class)
				.withConsumer(ExceptionalBiConsumer<AcknowledgeInput, UztaPlayer, Exception> { ack, _ ->
					if (ack.isAcknowledgement) {
						inputter.giveResources(trade)
						playerToTradeWith.giveResources(trade.inverse)
					}
				})
				.build()

		fireEvent(request)
	}

	@Throws(UnacceptableInputException::class)
	private fun checkResources(
			resources: BaseIntegerBag<NodeType>,
			player: UztaPlayer,
			message: String
	) {
		val resourcesLeftAfterGiving = player.getResourcesLeftAfter(resources)
		if (resourcesLeftAfterGiving.hasLackingItems()) {
			throw UnacceptableInputException(MessageFormat.format(
					message,
					player.name,
					presentResources(resources),
					resourcesLeftAfterGiving.stream()
							.filter { entry -> entry.right < 0 }
							.map { entry -> entry.left.getNumber(-entry.right) }
							.collect(joining(", "))
			))
		}
	}

	private fun presentResources(resources: BaseIntegerBag<NodeType>): String {
		return if (resources.isEmpty) {
			"nothing"
		} else resources.stream()
				.map { entry -> entry.left.getNumber(entry.right) }
				.collect(joining(", "))
	}

	companion object {

		val SUPPLY_RATIO = 3
	}
}
