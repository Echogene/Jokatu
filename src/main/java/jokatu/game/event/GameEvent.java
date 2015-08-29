package jokatu.game.event;

import org.jetbrains.annotations.NotNull;

/**
 * An event is something that happens in a game.
 */
public interface GameEvent {

	/**
	 * @return the message as presented to the users receiving the event
	 */
	@NotNull
	String getMessage();
}
