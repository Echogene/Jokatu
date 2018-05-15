package jokatu.game.games.uzta.input

import jokatu.game.games.uzta.graph.NodeType
import jokatu.game.input.Input

class InitialTradeRequest(val playerName: String?, val resource: NodeType) : Input {
	companion object {

		val SUPPLY: String? = null
	}
}
