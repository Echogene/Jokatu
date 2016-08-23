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
			@NotNull String name,
			@NotNull String label,
			@NotNull Form.FormFieldType type
	) {
		fields.add(new Form.FormField<>(name, label, type));
		return this;
	}

	@NotNull
	public <T> DialogFormBuilder withField(
			@NotNull String name,
			@NotNull String label,
			@NotNull Form.FormFieldType type,
			@NotNull T value
	) {
		fields.add(new Form.FormField<>(name, label, type, value));
		return this;
	}

	@NotNull
	@Override
	public DialogFormBuilder noöp() {
		return this;
	}
}
