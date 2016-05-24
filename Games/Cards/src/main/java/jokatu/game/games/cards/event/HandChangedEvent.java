package jokatu.game.games.cards.event;

import jokatu.game.event.GameEvent;
import jokatu.game.games.cards.player.CardPlayer;
import org.jetbrains.annotations.NotNull;

public interface HandChangedEvent extends GameEvent {
	@NotNull
	CardPlayer getPlayer();
}
