package jokatu.game.factory.game;

import jokatu.game.Game;
import org.jetbrains.annotations.NotNull;

public interface GameFactory<G extends Game<?, ?>> {

	@NotNull G produce();
}
