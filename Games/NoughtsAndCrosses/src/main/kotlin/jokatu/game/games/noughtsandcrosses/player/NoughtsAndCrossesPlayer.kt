package jokatu.game.games.noughtsandcrosses.player

import jokatu.game.games.noughtsandcrosses.input.NoughtOrCross
import jokatu.game.player.AbstractPlayer

class NoughtsAndCrossesPlayer(username: String) : AbstractPlayer(username) {
	var allegiance: NoughtOrCross? = null
}
