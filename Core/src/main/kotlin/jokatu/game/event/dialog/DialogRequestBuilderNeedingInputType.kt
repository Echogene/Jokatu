package jokatu.game.event.dialog

import jokatu.game.input.Input
import jokatu.game.player.Player
import kotlin.reflect.KClass

interface DialogRequestBuilderNeedingInputType<P : Player> {

	fun <I : Input> withInputType(inputType: KClass<I>): DialogRequestBuilderNeedingConsumer<P, I>
}
