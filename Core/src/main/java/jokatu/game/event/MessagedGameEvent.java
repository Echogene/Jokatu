package jokatu.game.event;

import org.jetbrains.annotations.NotNull;

/**
 * A {@link GameEvent} with a message.
 */
public interface MessagedGameEvent extends GameEvent {

	/**
	 * @return the message as presented to the users receiving the event
	 */
	@NotNull
	String getMessage();
}
