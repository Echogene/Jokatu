package jokatu.game.input;

import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;

/**
 * An exception thrown when a user's input is not acceptable.  This could mean that the user sent badly-formed input or
 * they sent it at the wrong time.  If one of these is thrown, it is probably an indication that either the frontend
 * has a bug that sends an input or something is tampering with the user's requests.
 */
public class UnacceptableInputException extends Exception {

	public UnacceptableInputException(@NotNull String message) {
		super(message);
	}

	public UnacceptableInputException(@NotNull String message, @NotNull Throwable cause) {
		super(message, cause);
	}

	public UnacceptableInputException(@NotNull String pattern, Object... arguments) {
		super(MessageFormat.format(pattern, arguments));
	}
}
