package jokatu.ui;

import org.jetbrains.annotations.NotNull;

public interface FormField extends FormElement {

	@NotNull
	String getName();

	@NotNull
	String getType();

	enum FormFieldType {
		TEXT,
		CHECKBOX,
		RANGE,
		NUMBER,
		SELECT
	}
}
