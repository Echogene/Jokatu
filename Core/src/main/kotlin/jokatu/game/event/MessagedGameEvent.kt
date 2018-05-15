package jokatu.game.event

/**
 * A [GameEvent] with a message.
 */
interface MessagedGameEvent : GameEvent {
	/**
	 * @return the message as presented to the users receiving the event
	 */
	val message: String
}
