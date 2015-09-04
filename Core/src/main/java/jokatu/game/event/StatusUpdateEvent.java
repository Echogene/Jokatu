package jokatu.game.event;

import jokatu.game.status.Status;
import org.jetbrains.annotations.NotNull;

/**
 * @author steven
 */
public interface StatusUpdateEvent extends GameEvent {

	@NotNull
	Status getStatus();
}
