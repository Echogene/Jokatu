package jokatu.game.event.dialog;

import jokatu.game.player.Player;
import org.jetbrains.annotations.NotNull;

public interface DialogRequestBuilderNeedingTitle<P extends Player> {

	@NotNull
	DialogRequestBuilderNeedingMessage<P> withTitle(@NotNull String title);
}
