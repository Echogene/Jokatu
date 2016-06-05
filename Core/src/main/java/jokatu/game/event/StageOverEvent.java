package jokatu.game.event;

import jokatu.game.input.AwaitingInputEvent;
import org.jetbrains.annotations.NotNull;

import static java.util.Collections.emptySet;

public class StageOverEvent extends AwaitingInputEvent implements GameEvent {

	public StageOverEvent() {
		// We are no longer waiting for any players to input.
		super(emptySet());
	}

	@NotNull
	@Override
	public String getMessage() {
		// This isn't actually returned to the client.
		// todo: decide whether all GameEvents should have a message
		return "End of stage";
	}
}
