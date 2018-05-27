package jokatu.game.event.dialog

import jokatu.game.event.GameEvent
import jokatu.game.input.Input
import jokatu.game.player.Player
import jokatu.ui.Dialog
import jokatu.ui.Form
import ophelia.function.ExceptionalBiConsumer
import kotlin.reflect.KClass

class DialogRequest<P : Player, I : Input> internal constructor(
		val inputClass: KClass<I>,
		val player: P,
		private val title: String,
		private val message: String,
		private val consumer: ExceptionalBiConsumer<in I, in P, *>,
		private val form: Form?,
		private val cancellable: Boolean
) : GameEvent {

	val dialog: Dialog
		get() = Dialog(title, message, form, cancellable)

	@Throws(Exception::class)
	fun accept(i: I) {
		consumer.accept(i, player)
	}

	internal fun getPlayer(): Player {
		return player
	}
}
