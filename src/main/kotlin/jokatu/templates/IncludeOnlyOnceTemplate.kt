package jokatu.templates

import groovy.lang.Writable
import groovy.text.markup.BaseTemplate
import groovy.text.markup.MarkupTemplateEngine
import groovy.text.markup.TemplateConfiguration
import java.io.IOException
import java.util.*

/**
 * After a template has been included, don't include it anywhere else in the current page stack.  This is done by
 * recursively sharing the same modifiable set of included paths and, when including, ignoring paths that have
 * previously been included anywhere in this page.
 */
open class IncludeOnlyOnceTemplate(
		private val engine: MarkupTemplateEngine,
		model: Map<*, *>,
		private val modelTypes: Map<String, String>?,
		configuration: TemplateConfiguration
) : BaseTemplate(engine, model, modelTypes, configuration) {

	private var includedPaths: MutableSet<String> = HashSet()

	@Throws(IOException::class, ClassNotFoundException::class)
	override fun includeGroovy(templatePath: String) {
		val added = includedPaths.add(templatePath)
		if (added) {
			val resource = engine.resolveTemplate(templatePath)
			val included = engine.createTypeCheckedModelTemplate(resource, modelTypes).make(model)
			shareIncludes(included)
			included.writeTo(out)
		}
	}

	private fun shareIncludes(included: Writable) {
		if (included is IncludeOnlyOnceTemplate) {
			included.setIncludedPaths(includedPaths)
		}
	}

	@Throws(IOException::class, ClassNotFoundException::class)
	override fun layout(
			model: Map<*, *>,
			templateName: String,
			inheritModel: Boolean
	): Any {

		val submodel = if (inheritModel) forkModel(model) else model
		val resource = engine.resolveTemplate(templateName)
		val layout = engine.createTypeCheckedModelTemplate(resource, modelTypes).make(submodel)
		shareIncludes(layout)
		layout.writeTo(out)

		return this
	}

	private fun forkModel(m: Map<*, *>): Map<*, *> {
		val result = HashMap<Any?, Any?>()
		result.putAll(model)
		result.putAll(m)
		return result
	}

	private fun setIncludedPaths(includedPaths: MutableSet<String>) {
		this.includedPaths = includedPaths
	}

	override fun run(): Any? {
		return null
	}
}
