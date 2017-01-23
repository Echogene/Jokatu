package jokatu.ui;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CustomFormField<T, A> implements FormField {

	@NotNull
	private final String name;

	@NotNull
	private final String label;

	/**
	 * The name of the custom form element to be created, such as '{@code JInteger}'.
	 */
	@NotNull
	private final String elementName;

	@Nullable
	private final T value;

	@NotNull
	private final A attributes;

	public CustomFormField(
			@NotNull String name,
			@NotNull String label,
			@NotNull String elementName,
			@Nullable T value,
			@NotNull A attributes
	) {
		this.name = name;
		this.label = label;
		this.elementName = elementName;
		this.value = value;
		this.attributes = attributes;
	}

	@Override
	public @NotNull String getLabel() {
		return label;
	}

	@Override
	public @NotNull String getName() {
		return name;
	}

	@Override
	public @NotNull String getType() {
		return elementName;
	}

	@Nullable
	public T getValue() {
		return value;
	}

	@NotNull
	public A getAttributes() {
		return attributes;
	}
}
