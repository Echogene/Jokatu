package jokatu.components.stomp

import jokatu.game.Game
import jokatu.game.player.Player
import jokatu.game.player.PlayerStatus
import ophelia.kotlin.number.AnnotatedInt


abstract class Destination<T: Any> {
	protected abstract val destination: String
	override fun toString() = destination
}

class UnknownDestination(override val destination: String): Destination<Any>()

abstract class Topic<T: Any>(string: String): Destination<T>() {
	override val destination = "/topic/$string"
	override fun toString() = destination
}

class GameStatus(game: Game<*>): Topic<String>("status.game.${game.identifier}")

class PublicMessage(game: Game<*>): Topic<String>("public.game.${game.identifier}")

class GameResult(game: Game<*>): Topic<String>("result.game.${game.identifier}")

class GameObservers(game: Game<*>): Topic<Set<String>>("observers.game.${game.identifier}")

class GamePlayers(game: Game<*>): Topic<Set<PlayerStatus>>("players.game.${game.identifier}")

class GameAdvance(game: Game<*>): Topic<Boolean>("advance.game.${game.identifier}")

class AwaitingInput(game: Game<*>): Topic<Boolean>("awaiting.game.${game.identifier}")

class PrivateMessage(game: Game<*>): Topic<String>("private.game.${game.identifier}")

class PlayerScore(game: Game<*>, player: Player): Topic<AnnotatedInt>("score.game.${game.identifier}.${player.name}")