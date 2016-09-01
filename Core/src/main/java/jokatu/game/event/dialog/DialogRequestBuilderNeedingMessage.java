package jokatu.game.event.dialog;

import jokatu.game.player.Player;
import org.jetbrains.annotations.NotNull;

public interface DialogRequestBuilderNeedingMessage<P extends Player> {
	@NotNull
	DialogRequestBuilderNeedingInputType<P> withMessage(@NotNull String message);
}
