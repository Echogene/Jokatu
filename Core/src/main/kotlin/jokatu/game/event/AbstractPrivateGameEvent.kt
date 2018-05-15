package jokatu.game.event

import jokatu.game.player.Player

/**
 * An abstract version of a private game event that stores the collection of players for whom it is private.
 */
abstract class AbstractPrivateGameEvent protected constructor(override val players: Collection<Player>) : PrivateGameEvent
