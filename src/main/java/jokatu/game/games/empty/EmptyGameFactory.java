package jokatu.game.games.empty;

import jokatu.game.GameID;
import jokatu.game.factory.Factory;
import jokatu.game.factory.game.AbstractGameFactory;
import org.jetbrains.annotations.NotNull;

import static jokatu.game.games.empty.EmptyGame.EMPTY_GAME;

@Factory(gameName = EMPTY_GAME)
public class EmptyGameFactory extends AbstractGameFactory<EmptyGame> {

	@NotNull
	@Override
	protected EmptyGame produce(GameID gameID) {
		return new EmptyGame(gameID);
	}
}
