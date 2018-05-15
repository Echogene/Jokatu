package jokatu.game.event.dialog

import jokatu.game.player.Player

interface DialogRequestBuilderNeedingTitle<P : Player> {

	fun withTitle(title: String): DialogRequestBuilderNeedingMessage<P>
}
