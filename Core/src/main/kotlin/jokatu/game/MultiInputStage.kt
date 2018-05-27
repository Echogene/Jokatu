package jokatu.game

import jokatu.game.event.GameEvent
import jokatu.game.input.Input
import jokatu.game.input.InputAcceptor
import jokatu.game.player.Player
import jokatu.game.stage.Stage
import ophelia.event.observable.AbstractSynchronousObservable
import ophelia.exceptions.StackedException
import ophelia.exceptions.voidmaybe.VoidMaybe.wrap
import ophelia.exceptions.voidmaybe.VoidMaybeCollectors.merge
import java.util.*
import java.util.stream.Collectors
import kotlin.reflect.KClass

/**
 * A stage that can accept multiple types of [Input]s by using some [InputAcceptor]s
 */
abstract class MultiInputStage : AbstractSynchronousObservable<GameEvent>(), Stage<GameEvent> {

	private val inputAcceptors = ArrayList<InputAcceptor<out GameEvent>>()

	protected fun addInputAcceptor(inputAcceptor: InputAcceptor<out GameEvent>) {
		inputAcceptors.add(inputAcceptor)
		inputAcceptor.observe(::fireEvent)
	}

	override val acceptedInputs: Collection<KClass<out Input>>
		get() = inputAcceptors.stream()
				.map { it.acceptedInputs }
				.flatMap { it.stream() }
				.collect(Collectors.toSet())

	@Throws(StackedException::class)
	override fun accept(input: Input, player: Player) {
		inputAcceptors.stream()
				.map(wrap { acceptor -> acceptor.accept(input, player) })
				.collect(merge())
				.throwOnFailure()
	}
}
