package jokatu.game.games.sevens.event;

import jokatu.game.event.GameEvent;
import jokatu.game.games.sevens.player.SevensPlayer;
import org.jetbrains.annotations.NotNull;

public interface HandChangedEvent extends GameEvent {
	@NotNull
	SevensPlayer getPlayer();
}
