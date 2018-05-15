package jokatu.game.event

/**
 * An event that occurs that is public to all observers and players.
 * @author steven
 */
interface PublicGameEvent : MessagedGameEvent

class StandardPublicGameEvent(override val message: String) : PublicGameEvent
