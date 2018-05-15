package jokatu.game.input

import jokatu.game.event.GameEvent
import jokatu.game.player.Player

/**
 * These are [AbstractInputAcceptor]s that can fire any [GameEvent].
 */
abstract class AnyEventInputAcceptor<I : Input, P : Player> : AbstractInputAcceptor<I, P, GameEvent>()
