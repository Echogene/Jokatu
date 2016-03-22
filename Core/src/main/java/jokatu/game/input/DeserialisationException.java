package jokatu.game.input;

import java.text.MessageFormat;
import java.util.Map;

public class DeserialisationException extends Exception {
	public DeserialisationException(Map<String, Object> json, String reason) {
		super(MessageFormat.format("Failed to serialise {0}\n\t{1}", json, reason));
	}
}
