package jokatu.game.event.dialog

import jokatu.game.player.Player

interface DialogRequestBuilderNeedingMessage<P : Player> {
	fun withMessage(message: String): DialogRequestBuilderNeedingInputType<P>
}
