package jokatu.templates;

import groovy.lang.Writable;
import groovy.text.markup.BaseTemplate;
import groovy.text.markup.MarkupTemplateEngine;
import groovy.text.markup.TemplateConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * After a template has been included, don't include it anywhere else in the current page stack.  This is done by
 * recursively sharing the same modifiable set of included paths and, when including, ignoring paths that have
 * previously been included anywhere in this page.
 */
public class IncludeOnlyOnceTemplate extends BaseTemplate {

	private final MarkupTemplateEngine engine;
	private final Map<String, String> modelTypes;

	private Set<String> includedPaths = new HashSet<>();

	public IncludeOnlyOnceTemplate(
			MarkupTemplateEngine engine,
			Map model,
			Map<String, String> modelTypes,
			TemplateConfiguration configuration
	) {
		super(engine, model, modelTypes, configuration);
		this.engine = engine;
		this.modelTypes = modelTypes;
	}

	@Override
	public void includeGroovy(String templatePath) throws IOException, ClassNotFoundException {
		boolean added = includedPaths.add(templatePath);
		if (added) {
			URL resource = engine.resolveTemplate(templatePath);
			Writable included = engine.createTypeCheckedModelTemplate(resource, modelTypes).make(getModel());
			shareIncludes(included);
			included.writeTo(getOut());
		}
	}

	private void shareIncludes(Writable included) {
		if (included instanceof IncludeOnlyOnceTemplate) {
			IncludeOnlyOnceTemplate includedTemplate = (IncludeOnlyOnceTemplate) included;
			includedTemplate.setIncludedPaths(includedPaths);
		}
	}

	@Override
	public Object layout(
			Map model,
			String templateName,
			boolean inheritModel
	) throws IOException, ClassNotFoundException {

		Map submodel = inheritModel ? forkModel(model) : model;
		URL resource = engine.resolveTemplate(templateName);
		Writable layout = engine.createTypeCheckedModelTemplate(resource, modelTypes).make(submodel);
		shareIncludes(layout);
		layout.writeTo(getOut());

		return this;
	}

	private Map forkModel(Map m) {
		Map result = new HashMap();
		result.putAll(getModel());
		result.putAll(m);
		return result;
	}

	private void setIncludedPaths(@NotNull Set<String> includedPaths) {
		this.includedPaths = includedPaths;
	}

	@Override
	public Object run() {
		return null;
	}
}
