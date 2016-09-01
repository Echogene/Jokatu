package jokatu.game.event.dialog;

import jokatu.game.input.Input;
import jokatu.game.player.Player;
import ophelia.function.ExceptionalBiConsumer;
import org.jetbrains.annotations.NotNull;

public interface DialogRequestBuilderNeedingConsumer<P extends Player, I extends Input> {

	@NotNull
	MainDialogRequestBuilder<P, I> withConsumer(@NotNull ExceptionalBiConsumer<? super I, ? super P, ?> consumer);
}
