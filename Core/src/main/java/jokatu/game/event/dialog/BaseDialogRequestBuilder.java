package jokatu.game.event.dialog;

import jokatu.game.input.Input;
import jokatu.game.player.Player;
import jokatu.ui.Form;
import org.jetbrains.annotations.NotNull;

class BaseDialogRequestBuilder<P extends Player, I extends Input> implements MainDialogRequestBuilder<P, I> {
	private final Class<I> expectedInput;
	private final P player;
	private final String title;
	private final String message;

	private Form form;

	BaseDialogRequestBuilder(@NotNull Class<I> expectedInput, @NotNull P player, @NotNull String title, @NotNull String message) {
		this.expectedInput = expectedInput;
		this.player = player;
		this.title = title;
		this.message = message;
	}

	@Override
	@NotNull
	public DialogRequest<P, I> build() {
		return new DialogRequest<>(expectedInput, player, title, message, form);
	}

	@Override
	@NotNull
	public MainDialogRequestBuilder<P, I> noöp() {
		return this;
	}

	@NotNull
	@Override
	public MainDialogRequestBuilder<P, I> withForm(@NotNull Form form) {
		this.form = form;
		return this;
	}
}
