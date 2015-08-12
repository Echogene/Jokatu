package jokatu.game.games.echo;

import jokatu.game.AbstractGame;
import jokatu.game.GameID;
import jokatu.game.Status;
import jokatu.game.input.UnacceptableInputException;
import jokatu.game.joining.CannotJoinGameException;
import ophelia.collections.BaseCollection;
import ophelia.collections.UnmodifiableCollection;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Collections.newSetFromMap;
import static jokatu.game.Status.IN_PROGRESS;

public class EchoGame extends AbstractGame<EchoPlayer, EchoInput, BaseCollection<EchoPlayer>, Echo> {

	public static final String ECHO = "Echo";
	private final Collection<EchoPlayer> players = newSetFromMap(new ConcurrentHashMap<>());

	protected EchoGame(GameID identifier) {
		super(identifier);
	}

	@NotNull
	@Override
	public String getGameName() {
		return ECHO;
	}

	@NotNull
	@Override
	public BaseCollection<EchoPlayer> getPlayers() {
		return new UnmodifiableCollection<>(players);
	}

	@Override
	public void join(@NotNull EchoPlayer player) throws CannotJoinGameException {
		players.add(player);
	}

	@NotNull
	@Override
	public Status getStatus() {
		return IN_PROGRESS;
	}

	@Override
	public void accept(@NotNull EchoInput input, EchoPlayer player) throws UnacceptableInputException {
		fireEvent(new Echo(input, player));
	}
}
