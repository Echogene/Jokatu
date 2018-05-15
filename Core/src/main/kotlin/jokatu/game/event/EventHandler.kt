package jokatu.game.event

import jokatu.game.Game

/**
 * Listens to events and handles them.
 */
interface EventHandler {
	fun handle(game: Game<*>, e: GameEvent)
}
