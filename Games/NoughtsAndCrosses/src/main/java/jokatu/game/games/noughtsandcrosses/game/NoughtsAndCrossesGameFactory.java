package jokatu.game.games.noughtsandcrosses.game;

import jokatu.game.AbstractGameFactory;
import jokatu.game.GameID;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class NoughtsAndCrossesGameFactory extends AbstractGameFactory {

	@NotNull
	@Override
	protected NoughtsAndCrossesGame produce(@NotNull GameID gameID, @NotNull String creatorName) {
		return new NoughtsAndCrossesGame(gameID);
	}
}
