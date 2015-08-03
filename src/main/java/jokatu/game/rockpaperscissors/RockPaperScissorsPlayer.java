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
}
