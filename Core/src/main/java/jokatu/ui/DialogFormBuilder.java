package jokatu.ui;

import ophelia.builder.Builder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DialogFormBuilder implements Builder<Form, DialogFormBuilder> {

	private final List<FormElement> elements = new ArrayList<>();

	@NotNull
	@Override
	public Form build() {
		return new Form(elements);
	}

	@NotNull
	public DialogFormBuilder withDiv(@NotNull String label) {
		elements.add(new FormDiv(label));
		return this;
	}

	@NotNull
	public DialogFormBuilder withField(
			@NotNull FormField field
	) {
		elements.add(field);
		return this;
	}

	@NotNull
	public DialogFormBuilder withFields(@NotNull List<FormField> fields) {
		this.elements.addAll(fields);
		return this;
	}

	@NotNull
	@Override
	public DialogFormBuilder noöp() {
		return this;
	}
}
