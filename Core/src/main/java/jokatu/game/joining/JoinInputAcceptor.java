package jokatu.game.joining;

import jokatu.game.event.StageOverEvent;
import jokatu.game.input.InputAcceptor;
import jokatu.game.player.Player;
import jokatu.game.status.StandardTextStatus;
import ophelia.collections.set.bounded.BoundedPair;

// todo: make this accept more than bounded pairs and change the status code so that it doesn't refer to input
public class JoinInputAcceptor<P extends Player> extends InputAcceptor<JoinInput, P> {

	private final Class<P> playerClass;
	private final BoundedPair<P> players;
	private final StandardTextStatus status;

	public JoinInputAcceptor(Class<P> playerClass, BoundedPair<P> players, StandardTextStatus status) {
		this.playerClass = playerClass;
		this.players = players;
		this.status = status;
	}


	@Override
	protected Class<JoinInput> getInputClass() {
		return JoinInput.class;
	}

	@Override
	protected Class<P> getPlayerClass() {
		return playerClass;
	}

	@Override
	protected void acceptCastInputAndPlayer(JoinInput input, P inputter) throws Exception {
		checkCanJoin();
		players.add(inputter);
		if (players.size() == 2) {
			P player1 = players.getFirst();
			assert player1 != null;
			P player2 = players.getSecond();
			assert player2 != null;
			status.setText("Awaiting input from " + player1.getName() + " and " + player2.getName());
			fireEvent(new StageOverEvent());
		} else {
			status.setText("Waiting for one more player to join");
		}
		fireEvent(new PlayerJoinedEvent(inputter));
	}

	private void checkCanJoin() throws CannotJoinGameException {
		if (players.size() > 1) {
			throw new GameFullException("Rock-paper-scissors supports two players");
		}
	}
}
