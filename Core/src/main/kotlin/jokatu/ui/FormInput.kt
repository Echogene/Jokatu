package jokatu.ui

class FormInput<T> @JvmOverloads constructor(
		override val name: String,
		override val label: String,
		private val fieldType: FormField.FormFieldType,
		val value: T? = null
) : FormField {
	override val type: String
		get() = fieldType.toString().toLowerCase()
}
