package jokatu.game.result;

/**
 * A standard result for any game.
 */
public enum Result {
	WIN("wins", "win"),
	DRAW("draws", "draw"),
	LOSE("loses", "lose");

	private final String singular3rdPersonPresent;
	private final String plural3rdPersonPresent;

	Result(String singular3rdPersonPresent, String plural3rdPersonPresent) {
		this.singular3rdPersonPresent = singular3rdPersonPresent;
		this.plural3rdPersonPresent = plural3rdPersonPresent;
	}

	public String get3rdPersonPresent(int number) {
		return number == 1 ? singular3rdPersonPresent : plural3rdPersonPresent;
	}
}
