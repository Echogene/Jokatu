package jokatu.game.games.sevens.event

import jokatu.game.event.MessagedGameEvent
import jokatu.game.games.sevens.player.SevensPlayer

interface HandChangedEvent : MessagedGameEvent {
	val player: SevensPlayer
}
