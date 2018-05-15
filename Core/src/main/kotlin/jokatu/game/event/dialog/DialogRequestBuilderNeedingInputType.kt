package jokatu.game.event.dialog

import jokatu.game.input.Input
import jokatu.game.player.Player

interface DialogRequestBuilderNeedingInputType<P : Player> {

	fun <I : Input> withInputType(inputType: Class<I>): DialogRequestBuilderNeedingConsumer<P, I>
}
