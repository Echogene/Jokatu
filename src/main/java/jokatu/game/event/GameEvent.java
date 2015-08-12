package jokatu.game.event;

import jokatu.game.user.player.Player;
import ophelia.collections.BaseCollection;
import org.jetbrains.annotations.NotNull;

public interface GameEvent<P extends Player> {

	@NotNull
	BaseCollection<P> getPlayers();
}
