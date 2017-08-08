package jokatu.game.event.dialog;

import jokatu.game.input.Input;
import jokatu.game.player.Player;
import org.jetbrains.annotations.NotNull;

public interface DialogRequestBuilderNeedingInputType<P extends Player> {

	@NotNull
	<I extends Input> DialogRequestBuilderNeedingConsumer<P, I> withInputType(@NotNull Class<I> inputType);
}
