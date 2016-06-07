package jokatu.game.event;

import jokatu.game.input.AwaitingInputEvent;

import static java.util.Collections.emptySet;

public class StageOverEvent extends AwaitingInputEvent implements GameEvent {

	public StageOverEvent() {
		// We are no longer waiting for any players to input.
		super(emptySet());
	}
}
