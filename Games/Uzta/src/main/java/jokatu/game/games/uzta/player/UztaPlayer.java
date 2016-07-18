package jokatu.game.games.uzta.player;

import jokatu.game.games.uzta.game.UztaColour;
import jokatu.game.player.StandardPlayer;
import org.jetbrains.annotations.Nullable;

public class UztaPlayer extends StandardPlayer {
	private UztaColour colour;

	public UztaPlayer(String username) {
		super(username);
	}

	public void setColour(@Nullable UztaColour colour) {
		this.colour = colour;
	}

	@Nullable
	public UztaColour getColour() {
		return colour;
	}
}
