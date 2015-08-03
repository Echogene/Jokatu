package jokatu.game.rockpaperscissors;

import jokatu.game.factory.Factory;
import jokatu.game.factory.player.PlayerFactory;
import org.jetbrains.annotations.NotNull;

import static jokatu.game.rockpaperscissors.RockPaperScissorsGame.ROCK_PAPER_SCISSORS;

@Factory(gameName = ROCK_PAPER_SCISSORS)
public class RockPaperScissorsPlayerFactory implements PlayerFactory<RockPaperScissorsPlayer> {
	@NotNull
	@Override
	public RockPaperScissorsPlayer produce(@NotNull String username) {
		return new RockPaperScissorsPlayer(username);
	}
}
