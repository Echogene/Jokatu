package jokatu.ui;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Form {

	private final List<FormField<?>> fields;

	Form(List<FormField<?>> fields) {
		this.fields = fields;
	}

	public List<FormField<?>> getFields() {
		return fields;
	}

	public enum FormFieldType {
		TEXT,
		CHECKBOX,
		RANGE
	}

	public static class FormField<T> {

		@NotNull
		private final String name;

		@NotNull
		private final String label;

		@NotNull
		private final Form.FormFieldType type;

		@Nullable
		private final T value;

		FormField(@NotNull String name, @NotNull String label, @NotNull Form.FormFieldType type) {
			this(name, label, type, null);
		}

		FormField(@NotNull String name, @NotNull String label, @NotNull Form.FormFieldType type, @Nullable T value) {
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
}
