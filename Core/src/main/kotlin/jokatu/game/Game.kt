package jokatu.game

import com.fasterxml.jackson.annotation.JsonIgnore
import jokatu.game.event.GameEvent
import jokatu.game.exception.GameException
import jokatu.game.input.Input
import jokatu.game.player.Player
import jokatu.game.stage.Stage
import jokatu.game.stage.machine.StageMachine
import jokatu.identity.Identifiable
import ophelia.collections.set.UnmodifiableSet
import ophelia.event.observable.AbstractSynchronousObservable
import ophelia.event.observable.Observable
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean
import java.util.stream.Collectors

/**
 * A game has a collection of [Player]s and a current [Stage].
 * @author Steven Weston
 */
abstract class Game<P : Player> protected constructor(override val identifier: GameID) : AbstractSynchronousObservable<GameEvent>(), Identifiable<GameID>, Observable<GameEvent> {

	protected val players: MutableMap<String, P> = HashMap()

	private val currentStageStarted = AtomicBoolean(false)

	protected var stageMachine: StageMachine? = null

	abstract val gameName: String

	/**
	 * Gets the current stage and lazily starts it if it hasn't been started yet.
	 * @return the current stage that has also been started
	 */
	val currentStage: Stage<out GameEvent>?
		@JsonIgnore
		get() {
			val currentStage = stageMachine!!.currentStage
			if (!currentStageStarted.getAndSet(true) && currentStage != null) {
				currentStage.start()
			}
			return currentStage
		}

	@Throws(GameException::class)
	fun accept(input: Input, player: Player) {
		if (currentStage == null) {
			throw GameException(identifier, "The game hasn't started yet.")
		}
		try {
			currentStage!!.accept(input, player)
		} catch (e: Exception) {
			throw GameException(
					identifier,
					e,
					e.message ?: ""
			)
		}

	}


	fun getPlayerByName(name: String): P? {
		return players[name]
	}

	fun hasPlayer(name: String): Boolean {
		return getPlayerByName(name) != null
	}

	fun advanceStage() {
		currentStageStarted.set(false)
		val newStage = stageMachine!!.advance()
		newStage.observe(::fireEvent)
	}

	fun getPlayers(): UnmodifiableSet<P> {
		return UnmodifiableSet(players.values)
	}

	fun getOtherPlayers(name: String): Set<P> {
		return players.values.stream()
				.filter { p -> name != p.name }
				.collect(Collectors.toSet())
	}
}
