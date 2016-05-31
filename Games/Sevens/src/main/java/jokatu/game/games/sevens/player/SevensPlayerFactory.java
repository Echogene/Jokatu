package jokatu.game.games.sevens.player;

import jokatu.game.games.sevens.game.SevensGame;
import jokatu.game.player.AbstractPlayerFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class SevensPlayerFactory extends AbstractPlayerFactory<SevensPlayer, SevensGame> {

	@NotNull
	@Override
	protected Class<SevensGame> getGameClass() {
		return SevensGame.class;
	}

	@NotNull
	@Override
	protected SevensPlayer produceInCastGame(@NotNull SevensGame sevensGame, @NotNull String username) {
		SevensPlayer player = new SevensPlayer(username);
		sevensGame.register(player);
		return player;
	}
}
