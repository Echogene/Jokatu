package jokatu.game;

import jokatu.game.joining.CannotJoinGameException;
import jokatu.game.user.player.Player;
import jokatu.identity.Identifiable;

import java.util.Collection;

/**
 * @author Steven Weston
 */
public interface Game<P extends Player, C extends Collection<P>> extends Identifiable<GameID> {

	C getPlayers();

	void join(P player) throws CannotJoinGameException;

	Status getStatus();
}
