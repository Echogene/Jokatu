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

	public Dialog(@NotNull String title, @NotNull String message) {
		this(title, message, null);
	}

	public Dialog(@NotNull String title, @NotNull String message, @Nullable Form form) {
		this.title = title;
		this.message = message;
		this.form = form;
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
}
