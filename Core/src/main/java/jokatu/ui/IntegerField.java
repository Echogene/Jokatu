package jokatu.ui;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IntegerField extends CustomFormField<Integer, IntegerField.Attributes> {
	public IntegerField(
			@NotNull String name,
			@NotNull String label,
			@Nullable Integer value,
			@Nullable String positiveText,
			@Nullable String negativeText
	) {
		super(name, label, "JInteger", value, new Attributes(positiveText, negativeText));
	}

	static class Attributes {
		private final String positiveText;
		private final String negativeText;

		private Attributes(
				@Nullable String positiveText,
				@Nullable String negativeText
		) {
			this.positiveText = positiveText;
			this.negativeText = negativeText;
		}

		@Nullable
		public String getPositiveText() {
			return positiveText;
		}

		@Nullable
		public String getNegativeText() {
			return negativeText;
		}
	}
}
