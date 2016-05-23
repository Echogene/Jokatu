package jokatu.game.games.cards.player;

import jokatu.game.Game;
import jokatu.game.player.PlayerFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class CardPlayerFactory implements PlayerFactory<CardPlayer> {

	@NotNull
	@Override
	public CardPlayer produce(@NotNull Game<? extends CardPlayer> game, @NotNull String username) {
		return new CardPlayer(username);
	}
}
