package jokatu.game.games.uzta.graph;

import org.jetbrains.annotations.NotNull;

public enum NodeType {
	SQUARE("square", "squares"),
	CIRCLE("circle", "circles"),
	RHOMBUS("rhombus", "rhombi");

	@NotNull
	private final String singular;
	@NotNull
	private final String plural;

	NodeType(@NotNull String singular, @NotNull String plural) {
		this.singular = singular;
		this.plural = plural;
	}

	@NotNull
	public String getNumber(int number) {
		return number == 1 ? singular : plural;
	}
}
