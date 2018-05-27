package jokatu.game.stage

import jokatu.game.event.GameEvent
import jokatu.game.input.Input
import jokatu.game.player.Player
import kotlin.reflect.KClass

/**
 * A [SingleInputStage] that can fire any event.
 */
abstract class AnyEventSingleInputStage<I : Input, P : Player>(
		inputClass: KClass<I>,
		playerClass: KClass<P>
) : SingleInputStage<I, P, GameEvent>(inputClass, playerClass)
