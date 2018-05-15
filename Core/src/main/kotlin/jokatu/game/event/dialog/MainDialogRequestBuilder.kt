package jokatu.game.event.dialog

import jokatu.game.input.Input
import jokatu.game.player.Player
import jokatu.ui.Form
import ophelia.builder.Builder

interface MainDialogRequestBuilder<P : Player, I : Input> : Builder<DialogRequest<P, I>, MainDialogRequestBuilder<P, I>> {

	fun withForm(form: Form): MainDialogRequestBuilder<P, I>

	fun withoutCancelButton(): MainDialogRequestBuilder<P, I>
}
