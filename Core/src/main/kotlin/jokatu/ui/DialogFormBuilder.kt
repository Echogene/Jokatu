package jokatu.ui

import ophelia.builder.Builder
import java.util.*

class DialogFormBuilder : Builder<Form, DialogFormBuilder> {

	private val elements = ArrayList<FormElement>()

	override fun build(): Form {
		return Form(elements)
	}

	fun withDiv(label: String): DialogFormBuilder {
		elements.add(FormDiv(label))
		return this
	}

	fun withField(
			field: FormField
	): DialogFormBuilder {
		elements.add(field)
		return this
	}

	fun withFields(fields: List<FormField>): DialogFormBuilder {
		this.elements.addAll(fields)
		return this
	}

	override fun noöp(): DialogFormBuilder {
		return this
	}
}
