package jokatu.game.event.dialog

import jokatu.game.input.Input
import jokatu.game.player.Player
import jokatu.ui.Form
import ophelia.function.ExceptionalBiConsumer

internal class BaseDialogRequestBuilder<P : Player, I : Input>(
		private val expectedInput: Class<I>,
		private val player: P,
		private val title: String,
		private val message: String,
		private val consumer: ExceptionalBiConsumer<in I, in P, *>
) : MainDialogRequestBuilder<P, I> {

	private var form: Form? = null
	private var cancellable = true

	override fun build(): DialogRequest<P, I> {
		return DialogRequest(expectedInput, player, title, message, consumer, form, cancellable)
	}

	override fun noöp(): MainDialogRequestBuilder<P, I> {
		return this
	}

	override fun withForm(form: Form): MainDialogRequestBuilder<P, I> {
		this.form = form
		return this
	}

	override fun withoutCancelButton(): MainDialogRequestBuilder<P, I> {
		this.cancellable = false
		return this
	}
}
