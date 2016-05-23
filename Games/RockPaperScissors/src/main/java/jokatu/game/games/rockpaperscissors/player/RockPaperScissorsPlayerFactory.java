package jokatu.game.games.rockpaperscissors.player;

import jokatu.game.games.rockpaperscissors.game.RockPaperScissorsGame;
import jokatu.game.player.PlayerFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class RockPaperScissorsPlayerFactory extends PlayerFactory<RockPaperScissorsPlayer, RockPaperScissorsGame> {
	@NotNull
	@Override
	protected Class<RockPaperScissorsGame> getGameClass() {
		return RockPaperScissorsGame.class;
	}

	@NotNull
	@Override
	public RockPaperScissorsPlayer produceInCastGame(@NotNull RockPaperScissorsGame game, @NotNull String username) {
		return new RockPaperScissorsPlayer(username);
	}
}
