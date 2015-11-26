package jokatu.game.games.empty;

import jokatu.game.GameID;
import jokatu.game.factory.game.AbstractGameFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class EmptyGameFactory extends AbstractGameFactory {

	@NotNull
	@Override
	protected EmptyGame produce(GameID gameID) {
		return new EmptyGame(gameID);
	}
}
