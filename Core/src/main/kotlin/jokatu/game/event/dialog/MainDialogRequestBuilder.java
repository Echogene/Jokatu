package jokatu.game.event.dialog;

import jokatu.game.input.Input;
import jokatu.game.player.Player;
import jokatu.ui.Form;
import ophelia.builder.Builder;
import org.jetbrains.annotations.NotNull;

public interface MainDialogRequestBuilder<P extends Player, I extends Input> extends Builder<DialogRequest<P, I>, MainDialogRequestBuilder<P, I>> {

	@NotNull
	MainDialogRequestBuilder<P, I> withForm(@NotNull Form form);

	@NotNull
	MainDialogRequestBuilder<P, I> withoutCancelButton();
}
