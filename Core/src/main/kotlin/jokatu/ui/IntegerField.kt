package jokatu.ui

class IntegerField(
		name: String,
		label: String,
		value: Int?,
		positiveText: String?,
		negativeText: String?
) : CustomFormField<Int, IntegerField.Attributes>(name, label, "j-integer", value, Attributes(positiveText, negativeText)) {

	class Attributes constructor(
			val positiveText: String?,
			val negativeText: String?
	)
}
