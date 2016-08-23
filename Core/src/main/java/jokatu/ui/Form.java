package jokatu.ui;

import java.util.List;

public class Form {

	private final List<FormField<?>> fields;

	Form(List<FormField<?>> fields) {
		this.fields = fields;
	}

	public List<FormField<?>> getFields() {
		return fields;
	}

	public enum FormFieldType {
		TEXT,
		CHECKBOX,
		RANGE,
		NUMBER
	}

}
