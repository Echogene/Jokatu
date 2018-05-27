package jokatu.game.input

import jokatu.game.event.GameEvent
import jokatu.game.player.Player
import kotlin.reflect.KClass

/**
 * These are [AbstractInputAcceptor]s that can fire any [GameEvent].
 */
abstract class AnyEventInputAcceptor<I : Input, P : Player>(
		inputClass: KClass<I>,
		playerClass: KClass<P>
) : AbstractInputAcceptor<I, P, GameEvent>(inputClass, playerClass)
