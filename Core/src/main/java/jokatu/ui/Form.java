package jokatu.ui;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Form {

	private final List<FormElement> fields;

	Form(@NotNull List<FormElement> fields) {
		this.fields = fields;
	}

	@NotNull
	public List<FormElement> getFields() {
		return fields;
	}

	public enum FormFieldType {
		TEXT,
		CHECKBOX,
		RANGE,
		NUMBER,
		SELECT
	}
}
