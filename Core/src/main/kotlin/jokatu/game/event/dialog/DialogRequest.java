package jokatu.game.event.dialog;

import jokatu.game.event.GameEvent;
import jokatu.game.input.Input;
import jokatu.game.player.Player;
import jokatu.ui.Dialog;
import jokatu.ui.Form;
import ophelia.function.ExceptionalBiConsumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DialogRequest<P extends Player, I extends Input> implements GameEvent {

	private final Class<I> expectedInput;
	private final P player;
	private final String title;
	private final String message;
	private final Form form;
	private final ExceptionalBiConsumer<? super I, ? super P, ?> consumer;
	private final boolean cancellable;

	DialogRequest(
			@NotNull Class<I> expectedInput,
			@NotNull P player,
			@NotNull String title,
			@NotNull String message,
			@NotNull ExceptionalBiConsumer<? super I, ? super P, ?> consumer,
			@Nullable Form form,
			boolean cancellable
	) {
		this.expectedInput = expectedInput;
		this.player = player;
		this.title = title;
		this.message = message;
		this.form = form;
		this.consumer = consumer;
		this.cancellable = cancellable;
	}

	void accept(@NotNull I i) throws Exception {
		consumer.accept(i, player);
	}

	@NotNull
	Class<I> getInputClass() {
		return expectedInput;
	}

	@NotNull
	Dialog getDialog() {
		return new Dialog(title, message, form, cancellable);
	}

	@NotNull
	Player getPlayer() {
		return player;
	}
}
