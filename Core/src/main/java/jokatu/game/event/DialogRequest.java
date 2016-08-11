package jokatu.game.event;

import jokatu.game.input.Input;
import jokatu.game.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class DialogRequest<I extends Input> {

	private final Class<I> expectedInput;
	private final Player player;
	private final String title;
	private final String message;

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

		@NotNull
		String getTitle() {
			return title;
		}

		@NotNull
		String getMessage() {
			return message;
		}

		@NotNull
		Player getPlayer() {
			return player;
		}
	}
}
