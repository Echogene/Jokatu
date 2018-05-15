package jokatu.game.event

import jokatu.game.Game
import jokatu.game.input.AwaitingInputEvent
import jokatu.game.player.Player
import jokatu.game.stage.Stage

import java.util.Collections.emptySet

/**
 * An event that indicates that a [Stage] of a [Game] has ended.  By default, it specifies that all players
 * no longer need to input.
 */
open class StageOverEvent : AwaitingInputEvent(emptySet<Player>()), GameEvent // We are no longer waiting for any players to input.
