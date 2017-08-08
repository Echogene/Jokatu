package jokatu.ui;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Dialog {

	@NotNull
	private final String title;

	@NotNull
	private final String message;

	@Nullable
	private final Form form;

	private final boolean cancellable;

	public Dialog(@NotNull String title, @NotNull String message, @Nullable Form form, boolean cancellable) {
		this.title = title;
		this.message = message;
		this.form = form;
		this.cancellable = cancellable;
	}

	@NotNull
	public String getTitle() {
		return title;
	}

	@NotNull
	public String getMessage() {
		return message;
	}

	@Nullable
	public Form getForm() {
		return form;
	}

	public boolean isCancellable() {
		return cancellable;
	}
}
