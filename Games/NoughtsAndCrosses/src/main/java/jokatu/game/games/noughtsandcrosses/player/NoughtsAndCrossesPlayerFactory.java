package jokatu.game.games.noughtsandcrosses.player;

import jokatu.game.player.PlayerFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class NoughtsAndCrossesPlayerFactory implements PlayerFactory<NoughtsAndCrossesPlayer> {
	@NotNull
	@Override
	public NoughtsAndCrossesPlayer produce(@NotNull String username) {
		return new NoughtsAndCrossesPlayer(username);
	}
}
