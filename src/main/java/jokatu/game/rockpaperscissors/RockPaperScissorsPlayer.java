package jokatu.game.rockpaperscissors;

import jokatu.game.user.player.Player;

public class RockPaperScissorsPlayer implements Player {

	private final String name;

	public RockPaperScissorsPlayer(String username) {
		this.name = username;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		RockPaperScissorsPlayer that = (RockPaperScissorsPlayer) o;

		return name.equals(that.name);

	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
