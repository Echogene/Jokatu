package jokatu.game.games.echo.game

import jokatu.game.event.PublicGameEvent
import jokatu.game.games.echo.input.EchoInput
import jokatu.game.player.StandardPlayer

/**
 * An event that echoes the users input to everyone in the game.
 */
class Echo(input: EchoInput, player: StandardPlayer) : PublicGameEvent {

	override val message: String = player.name + " said: " + input.string

}
