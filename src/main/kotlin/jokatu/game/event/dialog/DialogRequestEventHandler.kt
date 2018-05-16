package jokatu.game.event.dialog

import jokatu.components.config.InputDeserialisers
import jokatu.components.ui.DialogRequestor
import jokatu.game.Game
import jokatu.game.event.EventHandler
import jokatu.game.event.SpecificEventHandler
import jokatu.game.exception.GameException
import jokatu.game.input.DeserialisationException
import jokatu.game.input.Input
import jokatu.game.input.acknowledge.AcknowledgeInputDeserialiser
import ophelia.function.ExceptionalConsumer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * An [EventHandler] for [DialogRequest]s.
 */
@Component
class DialogRequestEventHandler @Autowired constructor(
		private val dialogRequestor: DialogRequestor,
		private val inputDeserialisers: InputDeserialisers,
		private val acknowledgeInputDeserialiser: AcknowledgeInputDeserialiser
) : SpecificEventHandler<DialogRequest<*, *>>() {

	override val eventClass: Class<DialogRequest<*, *>>
		get() = DialogRequest::class.java

	override fun handleCastEvent(game: Game<*>, event: DialogRequest<*, *>) {
		dialogRequestor.requestDialog(
				event.dialog,
				event.player.name,
				game.identifier,
				ExceptionalConsumer { json ->
					val deserialiser = inputDeserialisers.getDeserialiser(event.inputClass) ?: throw GameException(
							game.identifier,
							"Could not find deserialiser for ${event.inputClass.simpleName} while responding to dialog."
					)
					try {
						val acknowledgeInput = acknowledgeInputDeserialiser.deserialise(json)
						if (!acknowledgeInput.isAcknowledgement) {
							// todo: add a way to handle cancels on the dialog request
							return@ExceptionalConsumer
						}
					} catch (ignore: DeserialisationException) {
					}

					try {
						val input = deserialiser.deserialise(json)
						@Suppress("UNCHECKED_CAST")
						(event as DialogRequest<*, Input>).accept(input)
					} catch (e: Exception) {
						val message = e.message
						throw GameException(
								game.identifier,
								e,
								message ?: e.javaClass.simpleName
						)
					}
				}
		)
	}
}
