package jokatu.game.event;

/**
 * @author Steven Weston
 */
public interface EventHandler<E extends GameEvent> {

	void handle(E e);
}
