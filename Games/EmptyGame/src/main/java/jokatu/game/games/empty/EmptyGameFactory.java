package jokatu.game.games.empty;

import jokatu.game.GameID;
import jokatu.game.factory.game.AbstractGameFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class EmptyGameFactory extends AbstractGameFactory {

	@NotNull
	@Override
	protected EmptyGame produce(@NotNull GameID gameID, @NotNull String creatorName) {
		return new EmptyGame(gameID);
	}
}
