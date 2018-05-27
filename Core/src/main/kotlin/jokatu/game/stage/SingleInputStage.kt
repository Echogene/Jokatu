package jokatu.game.stage

import jokatu.game.event.GameEvent
import jokatu.game.input.AbstractInputAcceptor
import jokatu.game.input.Input
import jokatu.game.player.Player
import kotlin.reflect.KClass

/**
 * A [Stage] that only accepts one kind of [Input] from one kind of [Player].
 */
abstract class SingleInputStage<I : Input, P : Player, E : GameEvent>(
		inputClass: KClass<I>,
		playerClass: KClass<P>
) : AbstractInputAcceptor<I, P, E>(inputClass, playerClass), Stage<E>
