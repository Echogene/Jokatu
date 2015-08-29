package jokatu.game.games.rockpaperscissors;

import com.fasterxml.jackson.annotation.JsonValue;
import jokatu.game.event.StatusChangeEvent;
import jokatu.game.status.Status;
import ophelia.event.observable.AbstractSynchronousObservable;
import org.jetbrains.annotations.NotNull;

/**
 * @author steven
 */
public class RockPaperScissorsStatus extends AbstractSynchronousObservable<StatusChangeEvent> implements Status {

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
		fireEvent(new StatusChangeEvent() {
			@NotNull
			@Override
			public Status getStatus() {
				return RockPaperScissorsStatus.this;
			}

			@NotNull
			@Override
			public String getMessage() {
				return text;
			}
		});
	}
}
