package jokatu.game.input.acknowledge;

import jokatu.game.input.Input;

public class AcknowledgeInput implements Input {
	private final boolean value;

	public AcknowledgeInput(boolean value) {
		this.value = value;
	}

	public boolean isAcknowledgement() {
		return value;
	}
}
