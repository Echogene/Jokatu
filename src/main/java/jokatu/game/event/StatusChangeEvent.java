package jokatu.game.event;

import jokatu.game.status.GameStatus;
import org.jetbrains.annotations.NotNull;

/**
 * @author steven
 */
public interface StatusChangeEvent extends GameEvent {

	@NotNull
	GameStatus getStatus();
}
