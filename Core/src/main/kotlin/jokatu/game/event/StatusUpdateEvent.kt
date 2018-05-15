package jokatu.game.event

import jokatu.game.status.Status

/**
 * An event that occurs when a game changes in status.
 * @author steven
 */
interface StatusUpdateEvent : GameEvent {
	val status: Status
}
