package jokatu.game.status;

import com.fasterxml.jackson.annotation.JsonValue;
import jokatu.game.event.StatusUpdateEvent;
import ophelia.event.observable.AbstractSynchronousObservable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;

/**
 * A {@link Status} that fires a {@link StatusUpdateEvent} whenever its text is updated.
 * @author steven
 */
public class StandardTextStatus extends AbstractSynchronousObservable<StatusUpdateEvent> implements Status {

	private String text;

	@Nullable
	@JsonValue
	@Override
	public String getText() {
		return text;
	}

	public void setText(@NotNull String text, Object... arguments) {
		this.text = MessageFormat.format(text, arguments);
		fireEvent(() -> StandardTextStatus.this);
	}
}
