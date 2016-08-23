package jokatu.ui;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by steven on 23/08/16.
 */
public class FormField<T> {

	@NotNull
	private final String name;

	@NotNull
	private final String label;

	@NotNull
	private final Form.FormFieldType type;

	@Nullable
	private final T value;

	public FormField(@NotNull String name, @NotNull String label, @NotNull Form.FormFieldType type) {
		this(name, label, type, null);
	}

	public FormField(
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

	@NotNull
	public String getName() {
		return name;
	}

	@NotNull
	public String getLabel() {
		return label;
	}

	@NotNull
	public String getType() {
		return type.toString().toLowerCase();
	}

	@Nullable
	public T getValue() {
		return value;
	}
}
