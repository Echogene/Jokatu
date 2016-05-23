package jokatu.game.games.noughtsandcrosses.player;

import jokatu.game.Game;
import jokatu.game.player.PlayerFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class NoughtsAndCrossesPlayerFactory extends PlayerFactory<NoughtsAndCrossesPlayer> {
	@NotNull
	@Override
	public NoughtsAndCrossesPlayer produce(@NotNull Game<? extends NoughtsAndCrossesPlayer> game, @NotNull String username) {
		return new NoughtsAndCrossesPlayer(username);
	}
}
