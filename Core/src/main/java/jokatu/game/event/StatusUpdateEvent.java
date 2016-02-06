package jokatu.game.event;

import jokatu.game.status.Status;
import org.jetbrains.annotations.NotNull;

/**
 * An event that occurs when a game changes in status.
 * @author steven
 */
public interface StatusUpdateEvent extends GameEvent {

	@NotNull
	Status getStatus();

	@NotNull
	@Override
	default String getMessage() {
		return getStatus().getText();
	}
}
