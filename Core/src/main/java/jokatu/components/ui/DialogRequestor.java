package jokatu.components.ui;

import jokatu.game.GameID;
import jokatu.game.exception.GameException;
import jokatu.ui.Dialog;
import ophelia.function.ExceptionalConsumer;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface DialogRequestor {

	void requestDialog(
			@NotNull final Dialog dialog,
			@NotNull String playerName,
			@NotNull GameID gameId,
			@NotNull ExceptionalConsumer<Map<String, Object>, GameException> jsonConsumer
	);
}
