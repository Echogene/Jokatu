package jokatu.game.event;

import jokatu.game.Game;
import jokatu.game.input.AwaitingInputEvent;
import jokatu.game.stage.Stage;

import static java.util.Collections.emptySet;

/**
 * An event that indicates that a {@link Stage} of a {@link Game} has ended.  By default, it specifies that all players
 * no longer need to input.
 */
public class StageOverEvent extends AwaitingInputEvent implements GameEvent {

	public StageOverEvent() {
		// We are no longer waiting for any players to input.
		super(emptySet());
	}
}
