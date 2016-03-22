package jokatu.game.games.echo;

import jokatu.game.AbstractGame;
import jokatu.game.GameID;
import jokatu.game.event.AbstractPrivateGameEvent;
import jokatu.game.event.StatusUpdateEvent;
import jokatu.game.input.Input;
import jokatu.game.input.UnacceptableInputException;
import jokatu.game.joining.CannotJoinGameException;
import jokatu.game.status.Status;
import ophelia.collections.BaseCollection;
import ophelia.collections.set.EmptySet;
import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

public class EchoGame extends AbstractGame<EchoPlayer> {

	public static final String ECHO = "Echo";
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
		return EmptySet.emptySet();
	}

	@Override
	public void joinInternal(@NotNull EchoPlayer player) throws CannotJoinGameException {
	}

	@NotNull
	@Override
	public Status getStatus() {
		return () -> "The time is: " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
	}

	@Override
	public void accept(@NotNull Input input, @NotNull EchoPlayer player) throws UnacceptableInputException {
		if (input instanceof EchoInput) {
			EchoInput echoInput = (EchoInput) input;
			fireEvent(new Echo(echoInput, player));
			fireEvent(new AbstractPrivateGameEvent(Collections.singleton(player)) {
				@NotNull
				@Override
				public String getMessage() {
					return "You said: " + echoInput.getString();
				}
			});
		}
	}
}
