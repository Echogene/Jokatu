package jokatu.game.games.rockpaperscissors;

import jokatu.game.factory.player.PlayerFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class RockPaperScissorsPlayerFactory implements PlayerFactory<RockPaperScissorsPlayer> {
	@NotNull
	@Override
	public RockPaperScissorsPlayer produce(@NotNull String username) {
		return new RockPaperScissorsPlayer(username);
	}
}
