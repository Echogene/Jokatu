package jokatu.game.games.sevens.game;

import jokatu.game.AbstractGameFactory;
import jokatu.game.GameID;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class SevensGameFactory extends AbstractGameFactory<SevensGame> {

	@NotNull
	@Override
	protected SevensGame produce(@NotNull GameID gameID, @NotNull String creatorName) {
		return new SevensGame(gameID);
	}
}
