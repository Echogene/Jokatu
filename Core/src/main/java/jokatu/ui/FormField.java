package jokatu.ui;

import org.jetbrains.annotations.NotNull;

public interface FormField {

	@NotNull
	String getName();

	@NotNull
	String getLabel();

	@NotNull
	String getType();
}
