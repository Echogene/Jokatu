package jokatu.game.games.rockpaperscissors;

import com.fasterxml.jackson.annotation.JsonValue;
import jokatu.game.event.StatusUpdateEvent;
import jokatu.game.status.Status;
import ophelia.event.observable.AbstractSynchronousObservable;
import org.jetbrains.annotations.NotNull;

/**
 * @author steven
 */
public class RockPaperScissorsStatus extends AbstractSynchronousObservable<StatusUpdateEvent> implements Status {

	private String text;

	public RockPaperScissorsStatus(String text) {
		this.text = text;
	}

	@NotNull
	@JsonValue
	@Override
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		fireEvent(() -> RockPaperScissorsStatus.this);
	}
}
