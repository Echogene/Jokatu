package jokatu.game.event.dialog;

import jokatu.game.input.Input;
import jokatu.game.player.Player;
import ophelia.function.ExceptionalBiConsumer;
import org.jetbrains.annotations.NotNull;

public class DialogRequestBuilder<P extends Player> implements DialogRequestBuilderNeedingTitle {

	private final P player;

	private DialogRequestBuilder(@NotNull P player) {
		this.player = player;
	}

	@NotNull
	public static <P extends Player> DialogRequestBuilder<P> forPlayer(@NotNull P player) {
		return new DialogRequestBuilder<>(player);
	}

	@Override
	@NotNull
	public DialogRequestBuilderNeedingMessage<P> withTitle(@NotNull String title) {
		return new NeedingMessage(title);
	}

	private class NeedingMessage implements DialogRequestBuilderNeedingMessage<P> {
		private final String title;

		NeedingMessage(@NotNull String title) {
			this.title = title;
		}

		@NotNull
		@Override
		public DialogRequestBuilderNeedingInputType<P> withMessage(@NotNull String message) {
			return new NeedingInputType(message);
		}

		private class NeedingInputType implements DialogRequestBuilderNeedingInputType<P> {
			private final String message;

			NeedingInputType(@NotNull String message) {
				this.message = message;
			}

			@NotNull
			@Override
			public <I extends Input> DialogRequestBuilderNeedingConsumer<P, I> withInputType(@NotNull Class<I> inputType) {
				return new NeedingConsumer<>(inputType);
			}

			private class NeedingConsumer<I extends Input> implements DialogRequestBuilderNeedingConsumer<P, I> {
				private final Class<I> inputType;

				NeedingConsumer(@NotNull Class<I> inputType) {
					this.inputType = inputType;
				}

				@NotNull
				@Override
				public MainDialogRequestBuilder<P, I> withConsumer(@NotNull ExceptionalBiConsumer<? super I, ? super P, ?> consumer) {
					return new BaseDialogRequestBuilder<>(inputType, player, title, message, consumer);
				}
			}
		}
	}
}
