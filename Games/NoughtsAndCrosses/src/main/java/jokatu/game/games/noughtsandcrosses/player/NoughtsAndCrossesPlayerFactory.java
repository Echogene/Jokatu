package jokatu.game.games.noughtsandcrosses.player;

import jokatu.game.games.noughtsandcrosses.game.NoughtsAndCrossesGame;
import jokatu.game.player.PlayerFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class NoughtsAndCrossesPlayerFactory extends PlayerFactory<NoughtsAndCrossesPlayer, NoughtsAndCrossesGame> {
	@NotNull
	@Override
	protected Class<NoughtsAndCrossesGame> getGameClass() {
		return NoughtsAndCrossesGame.class;
	}

	@NotNull
	@Override
	protected NoughtsAndCrossesPlayer produceInCastGame(NoughtsAndCrossesGame noughtsAndCrossesGame, String username) {
		return new NoughtsAndCrossesPlayer(username);
	}
}
