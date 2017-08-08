package jokatu.game.games.sevens.event;

import jokatu.game.event.MessagedGameEvent;
import jokatu.game.games.sevens.player.SevensPlayer;
import org.jetbrains.annotations.NotNull;

interface HandChangedEvent extends MessagedGameEvent {
	@NotNull
	SevensPlayer getPlayer();
}
