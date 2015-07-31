package jokatu.game;

import jokatu.game.input.Input;
import jokatu.game.input.UnacceptableInputException;
import jokatu.game.joining.CannotJoinGameException;
import jokatu.game.user.player.Player;
import jokatu.identity.Identifiable;
import ophelia.collections.BaseCollection;

/**
 * @author Steven Weston
 */
public interface Game<P extends Player, I extends Input<P>, C extends BaseCollection<P>> extends Identifiable<GameID> {

	C getPlayers();

	void join(P player) throws CannotJoinGameException;

	Status getStatus();

	void accept(I input) throws UnacceptableInputException;
}
