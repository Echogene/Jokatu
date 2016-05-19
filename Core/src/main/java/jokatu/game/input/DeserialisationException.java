package jokatu.game.input;

import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.Map;

public class DeserialisationException extends Exception {
	public DeserialisationException(@NotNull Map<String, Object> json, @NotNull String reason) {
		super(MessageFormat.format("Failed to serialise {0}\n\t{1}", json, reason));
	}
}
