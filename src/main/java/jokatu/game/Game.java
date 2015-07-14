package jokatu.game;

import jokatu.game.user.player.Player;

import java.util.Collection;

/**
 * @author Steven Weston
 */
public interface Game<P extends Player, C extends Collection<P>> {

	C getPlayers();
}
