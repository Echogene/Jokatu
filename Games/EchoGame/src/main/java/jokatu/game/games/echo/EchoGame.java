package jokatu.game.games.echo;

import jokatu.game.AbstractGame;
import jokatu.game.GameID;
import jokatu.game.event.AbstractPrivateGameEvent;
import jokatu.game.event.StatusUpdateEvent;
import jokatu.game.input.UnacceptableInputException;
import jokatu.game.joining.CannotJoinGameException;
import jokatu.game.status.Status;
import ophelia.collections.BaseCollection;
import ophelia.collections.UnmodifiableCollection;
import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Collections.newSetFromMap;

public class EchoGame extends AbstractGame<EchoPlayer, EchoInput> {

	public static final String ECHO = "Echo";
	private final Collection<EchoPlayer> players = newSetFromMap(new ConcurrentHashMap<>());
	private final Timer timer;

	protected EchoGame(GameID identifier) {
		super(identifier);
		timer = new Timer();
		timer.scheduleAtFixedRate(
				new TimerTask() {
					@Override
					public void run() {
						fireEvent((StatusUpdateEvent) EchoGame.this::getStatus);
					}
				},
				0,
				1000
		);
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
	}

	@NotNull
	@Override
	public Status getStatus() {
		return () -> "The time is: " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
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
