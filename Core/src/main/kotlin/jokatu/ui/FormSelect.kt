package jokatu.ui

import jokatu.ui.FormField.FormFieldType.SELECT

class FormSelect(override val name: String, override val label: String, val options: List<Option>) : FormField {

	override val type: String
		get() = SELECT.toString().toLowerCase()

	class Option(val name: String, val label: String, val isSelected: Boolean)
}
