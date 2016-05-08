package jokatu.game.status;

import com.fasterxml.jackson.annotation.JsonValue;
import jokatu.game.event.StatusUpdateEvent;
import ophelia.event.observable.AbstractSynchronousObservable;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;

/**
 * @author steven
 */
public class StandardTextStatus extends AbstractSynchronousObservable<StatusUpdateEvent> implements Status {

	private String text;

	public StandardTextStatus() {}

	public StandardTextStatus(String text) {
		this.text = text;
	}

	@NotNull
	@JsonValue
	@Override
	public String getText() {
		return text;
	}

	public void setText(String text, Object... arguments) {
		this.text = MessageFormat.format(text, arguments);
		fireEvent(() -> StandardTextStatus.this);
	}
}
