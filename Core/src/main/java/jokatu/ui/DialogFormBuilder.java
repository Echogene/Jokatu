package jokatu.ui;

import ophelia.builder.Builder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DialogFormBuilder implements Builder<Form, DialogFormBuilder> {

	private final List<Form.FormField<?>> fields = new ArrayList<>();

	@NotNull
	@Override
	public Form build() {
		return new Form(fields);
	}

	@NotNull
	public DialogFormBuilder withField(
			@NotNull Form.FormField<?> field
	) {
		fields.add(field);
		return this;
	}

	@NotNull
	public DialogFormBuilder withFields(@NotNull List<Form.FormField<?>> fields) {
		this.fields.addAll(fields);
		return this;
	}

	@NotNull
	@Override
	public DialogFormBuilder noöp() {
		return this;
	}
}
