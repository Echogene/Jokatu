package jokatu.ui;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

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

	static class Attributes extends HashMap<String, String> {
		private Attributes(
				@Nullable String positiveText,
				@Nullable String negativeText
		) {
			if (positiveText != null) put("positivetext", positiveText);
			if (negativeText != null) put("negativetext", negativeText);
		}
	}
}
