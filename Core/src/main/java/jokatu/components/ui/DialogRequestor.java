package jokatu.components.ui;

import jokatu.game.GameID;
import jokatu.game.exception.GameException;
import ophelia.function.ExceptionalConsumer;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface DialogRequestor {

	void requestDialog(
			@NotNull final String title,
			@NotNull final String message,
			@NotNull String playerName,
			@NotNull GameID gameId,
			@NotNull ExceptionalConsumer<Map<String, Object>, GameException> jsonConsumer
	);
}
