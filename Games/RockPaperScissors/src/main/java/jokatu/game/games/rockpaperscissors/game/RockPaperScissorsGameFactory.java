package jokatu.game.games.rockpaperscissors.game;

import jokatu.game.AbstractGameFactory;
import jokatu.game.GameID;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class RockPaperScissorsGameFactory extends AbstractGameFactory<RockPaperScissorsGame> {

	@NotNull
	@Override
	protected RockPaperScissorsGame produce(@NotNull GameID gameID, @NotNull String creatorName) {
		return new RockPaperScissorsGame(gameID);
	}
}
