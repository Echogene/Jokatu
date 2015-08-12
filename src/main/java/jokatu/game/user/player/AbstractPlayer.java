package jokatu.game.user.player;

import jokatu.game.games.rockpaperscissors.RockPaperScissorsPlayer;
import org.jetbrains.annotations.NotNull;

public class AbstractPlayer implements Player {
	protected final String name;

	public AbstractPlayer(String username) {
		this.name = username;
	}

	@NotNull
	@Override
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
