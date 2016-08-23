package jokatu.ui;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FormInput<T> implements FormField {

	@NotNull
	private final String name;

	@NotNull
	private final String label;

	@NotNull
	private final Form.FormFieldType type;

	@Nullable
	private final T value;

	public FormInput(@NotNull String name, @NotNull String label, @NotNull Form.FormFieldType type) {
		this(name, label, type, null);
	}

	public FormInput(
			@NotNull String name,
			@NotNull String label,
			@NotNull Form.FormFieldType type,
			@Nullable T value
	) {
		this.name = name;
		this.label = label;
		this.type = type;
		this.value = value;
	}

	@Override
	@NotNull
	public String getName() {
		return name;
	}

	@Override
	@NotNull
	public String getLabel() {
		return label;
	}

	@Override
	@NotNull
	public String getType() {
		return type.toString().toLowerCase();
	}

	@Nullable
	public T getValue() {
		return value;
	}
}
