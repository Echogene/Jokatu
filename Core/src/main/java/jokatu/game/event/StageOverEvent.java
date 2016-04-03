package jokatu.game.event;

import org.jetbrains.annotations.NotNull;

public class StageOverEvent implements GameEvent {
	@NotNull
	@Override
	public String getMessage() {
		// This isn't actually returned to the client.
		// todo: decide whether all GameEvents should have a message
		return "End of stage";
	}
}
