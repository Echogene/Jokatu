package jokatu.game.games.uzta.input

import jokatu.game.games.uzta.graph.NodeType
import jokatu.game.input.Input
import ophelia.collections.bag.BaseIntegerBag

class SupplyTradeRequest(val trade: BaseIntegerBag<NodeType>) : Input
