package jokatu.game.event;

import jokatu.game.input.Input;
import jokatu.game.player.Player;
import jokatu.ui.Dialog;
import jokatu.ui.Form;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class DialogRequest<I extends Input> {

	private final Class<I> expectedInput;
	private final Player player;
	private final String title;
	private final String message;
	private Form form;

	private DialogRequest(@NotNull Class<I> expectedInput, @NotNull Player player, @NotNull String title, @NotNull String message) {
		this.expectedInput = expectedInput;
		this.player = player;
		this.title = title;
		this.message = message;
	}

	@NotNull
	public static <I extends Input> DialogRequest<I> requestDialogFor(
			@NotNull Class<I> expectedInput,
			@NotNull Player player,
			@NotNull String title,
			@NotNull String message
	) {
		return new DialogRequest<>(expectedInput, player, title, message);
	}

	public DialogRequest<I> withForm(@NotNull Form form) {
		this.form = form;
		return this;
	}

	public DialogRequestEvent then(@NotNull Consumer<? super I> consumer) {
		return new DialogRequestEvent(consumer);
	}

	public class DialogRequestEvent implements GameEvent {

		private final Consumer<? super I> consumer;

		private DialogRequestEvent(@NotNull Consumer<? super I> consumer) {
			this.consumer = consumer;
		}

		void accept(@NotNull I i) {
			consumer.accept(i);
		}

		@NotNull
		Class<I> getInputClass() {
			return expectedInput;
		}

		Dialog getDialog() {
			return new Dialog(title, message, form);
		}

		@NotNull
		Player getPlayer() {
			return player;
		}
	}
}
