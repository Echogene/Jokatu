package jokatu.ui

open class CustomFormField<T, A>(
		override val name: String,
		override val label: String,
		/**
		 * The name of the custom form element to be created, such as '`j-integer`'.
		 */
		private val elementName: String,
		val value: T?,
		val attributes: A
) : FormField {
	override val type: String
		get() = elementName
}
