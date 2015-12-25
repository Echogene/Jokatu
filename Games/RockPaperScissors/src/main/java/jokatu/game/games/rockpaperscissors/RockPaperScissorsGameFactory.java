package jokatu.game.games.rockpaperscissors;

import jokatu.game.GameID;
import jokatu.game.factory.game.AbstractGameFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class RockPaperScissorsGameFactory extends AbstractGameFactory {

	@NotNull
	@Override
	protected RockPaperScissorsGame produce(@NotNull GameID gameID, @NotNull String creatorName) {
		return new RockPaperScissorsGame(gameID);
	}
}
