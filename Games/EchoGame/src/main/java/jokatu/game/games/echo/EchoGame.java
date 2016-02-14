package jokatu.game.games.echo;

import jokatu.game.AbstractGame;
import jokatu.game.GameID;
import jokatu.game.event.AbstractPrivateGameEvent;
import jokatu.game.event.StatusUpdateEvent;
import jokatu.game.input.UnacceptableInputException;
import jokatu.game.joining.CannotJoinGameException;
import jokatu.game.status.GameStatus;
import ophelia.collections.BaseCollection;
import ophelia.collections.UnmodifiableCollection;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Collections.newSetFromMap;
import static jokatu.game.status.GameStatus.IN_PROGRESS;

public class EchoGame extends AbstractGame<EchoPlayer, EchoInput> {

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
	public void joinInternal(@NotNull EchoPlayer player) throws CannotJoinGameException {
		players.add(player);
		fireEvent((StatusUpdateEvent) EchoGame.this::getStatus);
	}

	@NotNull
	@Override
	public GameStatus getStatus() {
		return IN_PROGRESS;
	}

	@Override
	public void accept(@NotNull EchoInput input, @NotNull EchoPlayer player) throws UnacceptableInputException {
		fireEvent(new Echo(input, player));
		fireEvent(new AbstractPrivateGameEvent(Collections.singleton(player)) {
			@NotNull
			@Override
			public String getMessage() {
				return "You said: " + input.getString();
			}
		});
	}
}
