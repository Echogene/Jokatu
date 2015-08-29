package jokatu.game.event;

import jokatu.game.status.Status;
import org.jetbrains.annotations.NotNull;

/**
 * @author steven
 */
public interface StatusChangeEvent extends GameEvent {

	@NotNull
	Status getStatus();
}
