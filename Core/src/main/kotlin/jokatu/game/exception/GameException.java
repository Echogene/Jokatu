package jokatu.game.exception;

import jokatu.game.GameID;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;

/**
 * An exception that occurs within a particular game.
 * @author steven
 */
public class GameException extends Exception {

	private final GameID id;

	public GameException(@NotNull GameID id, @NotNull String message) {
		super(message);
		this.id = id;
	}

	public GameException(
			@NotNull GameID id,
			@NotNull Throwable cause,
			@NotNull String message
	) {
		super(message, cause);
		this.id = id;
	}

	public GameException(@NotNull GameID id, @NotNull Throwable cause, @NotNull String pattern, Object... arguments) {
		this(id, cause, MessageFormat.format(pattern, arguments));
	}

	public GameException(@NotNull GameID id, @NotNull String pattern, Object... arguments) {
		this(id, MessageFormat.format(pattern, arguments));
	}

	@SuppressWarnings("unused") // I think this can be converted to JSON using Jackson.
	@NotNull
	public GameID getId() {
		return id;
	}
}
