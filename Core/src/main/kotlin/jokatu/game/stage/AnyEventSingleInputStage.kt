package jokatu.game.stage

import jokatu.game.event.GameEvent
import jokatu.game.input.Input
import jokatu.game.player.Player

/**
 * A [SingleInputStage] that can fire any event.
 */
abstract class AnyEventSingleInputStage<I : Input, P : Player> : SingleInputStage<I, P, GameEvent>()
