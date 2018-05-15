package jokatu.game.event.dialog

import jokatu.game.input.Input
import jokatu.game.player.Player
import ophelia.function.ExceptionalBiConsumer

interface DialogRequestBuilderNeedingConsumer<P : Player, I : Input> {

	fun withConsumer(consumer: ExceptionalBiConsumer<in I, in P, *>): MainDialogRequestBuilder<P, I>
}
