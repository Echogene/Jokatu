package jokatu.game.games.rockpaperscissors.player;

import jokatu.game.Game;
import jokatu.game.player.PlayerFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class RockPaperScissorsPlayerFactory implements PlayerFactory<RockPaperScissorsPlayer> {
	@NotNull
	@Override
	public RockPaperScissorsPlayer produce(@NotNull Game<? extends RockPaperScissorsPlayer> game, @NotNull String username) {
		return new RockPaperScissorsPlayer(username);
	}
}
