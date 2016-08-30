package jokatu.ui;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static jokatu.ui.FormField.FormFieldType.SELECT;

public class FormSelect implements FormField {

	private final String name;
	private final String label;
	private final List<Option> options;

	public FormSelect(@NotNull String name, @NotNull String label, @NotNull List<Option> options) {
		this.name = name;
		this.label = label;
		this.options = options;
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
		return SELECT.toString().toLowerCase();
	}

	@NotNull
	public List<Option> getOptions() {
		return options;
	}

	public static class Option {

		private final String name;
		private final String label;
		private final boolean selected;

		public Option(@NotNull String name, @NotNull String label, boolean selected) {
			this.name = name;
			this.label = label;
			this.selected = selected;
		}

		@NotNull
		public String getName() {
			return name;
		}

		@NotNull
		public String getLabel() {
			return label;
		}

		public boolean isSelected() {
			return selected;
		}
	}
}
