package jokatu.game;

import jokatu.game.joining.CannotJoinGameException;
import jokatu.game.user.player.Player;
import jokatu.identity.Identifiable;

import java.util.Set;

/**
 * @author Steven Weston
 */
public interface Game<P extends Player> extends Identifiable<GameID> {

	Set<P> getPlayers();

	void join(P player) throws CannotJoinGameException;

	Status getStatus();
}
