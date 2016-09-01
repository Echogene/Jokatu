package jokatu.game.event.dialog;

import jokatu.game.event.GameEvent;
import jokatu.game.input.Input;
import jokatu.game.player.Player;
import jokatu.ui.Dialog;
import jokatu.ui.Form;
import ophelia.function.ExceptionalBiConsumer;
import org.jetbrains.annotations.NotNull;

public class DialogRequest<P extends Player, I extends Input> {

	private final Class<I> expectedInput;
	private final P player;
	private final String title;
	private final String message;
	private Form form;

	private DialogRequest(@NotNull Class<I> expectedInput, @NotNull P player, @NotNull String title, @NotNull String message) {
		this.expectedInput = expectedInput;
		this.player = player;
		this.title = title;
		this.message = message;
	}

	@NotNull
	public static <P extends Player, I extends Input> DialogRequest<P, I> requestDialogFor(
			@NotNull Class<I> expectedInput,
			@NotNull P player,
			@NotNull String title,
			@NotNull String message
	) {
		return new DialogRequest<>(expectedInput, player, title, message);
	}

	public DialogRequest<P, I> withForm(@NotNull Form form) {
		this.form = form;
		return this;
	}

	public DialogRequestEvent then(@NotNull ExceptionalBiConsumer<? super I, ? super P, ?> consumer) {
		return new DialogRequestEvent(consumer);
	}

	public class DialogRequestEvent implements GameEvent {

		private final ExceptionalBiConsumer<? super I, ? super P, ?> consumer;

		private DialogRequestEvent(@NotNull ExceptionalBiConsumer<? super I, ? super P, ?> consumer) {
			this.consumer = consumer;
		}

		void accept(@NotNull I i) throws Exception {
			consumer.accept(i, player);
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
