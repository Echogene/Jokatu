package jokatu.game.games.uzta.graph;

import org.jetbrains.annotations.NotNull;

public enum NodeType {
	SQUARE("square", "squares", "□"),
	CIRCLE("circle", "circles", "○"),
	RHOMBUS("rhombus", "rhombi", "◇");

	@NotNull
	private final String singular;
	@NotNull
	private final String plural;
	@NotNull
	private final String symbol;

	NodeType(@NotNull String singular, @NotNull String plural, @NotNull String symbol) {
		this.singular = singular;
		this.plural = plural;
		this.symbol = symbol;
	}

	@NotNull
	public String getSymbol() {
		return symbol;
	}

	@NotNull
	public String getNumber(int number) {
		return number + " " + (number == 1 ? singular : plural);
	}

	@NotNull
	public String getPlural() {
		return plural;
	}
}
