package jokatu.game.stage

import jokatu.game.event.GameEvent
import jokatu.game.input.AbstractInputAcceptor
import jokatu.game.input.Input
import jokatu.game.player.Player

/**
 * A [Stage] that only accepts one kind of [Input] from one kind of [Player].
 */
abstract class SingleInputStage<I : Input, P : Player, E : GameEvent> : AbstractInputAcceptor<I, P, E>(), Stage<E>
