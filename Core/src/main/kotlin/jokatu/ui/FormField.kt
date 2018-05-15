package jokatu.ui

interface FormField : FormElement {

	val name: String
	val type: String

	enum class FormFieldType {
		TEXT,
		CHECKBOX,
		RANGE,
		NUMBER,
		SELECT;
	}
}
