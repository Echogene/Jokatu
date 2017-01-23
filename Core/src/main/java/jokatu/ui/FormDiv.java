package jokatu.ui;

import org.jetbrains.annotations.NotNull;

public class FormDiv implements FormElement {

	@NotNull
	private final String label;

	public FormDiv(@NotNull String label) {
		this.label = label;
	}

	@NotNull
	@Override
	public String getLabel() {
		return label;
	}
}
