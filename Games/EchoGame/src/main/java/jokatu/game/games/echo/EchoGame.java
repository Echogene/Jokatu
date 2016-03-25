package jokatu.game.games.echo;

import jokatu.game.Game;
import jokatu.game.GameID;
import jokatu.game.event.AbstractPrivateGameEvent;
import jokatu.game.event.StatusUpdateEvent;
import jokatu.game.input.Input;
import jokatu.game.input.InputAcceptor;
import jokatu.game.player.Player;
import jokatu.game.status.Status;
import ophelia.collections.BaseCollection;
import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import static ophelia.collections.set.EmptySet.emptySet;

public class EchoGame extends Game<EchoPlayer> {

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

	@Override
	protected BaseCollection<InputAcceptor<? extends Input, ? extends Player>> getInputAcceptors() {
		return emptySet();
	}

	@NotNull
	@Override
	public String getGameName() {
		return ECHO;
	}

	@NotNull
	@Override
	public BaseCollection<EchoPlayer> getPlayers() {
		return emptySet();
	}

	@NotNull
	@Override
	public Status getStatus() {
		return () -> "The time is: " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
	}

	@Override
	public void accept(@NotNull Input input, @NotNull Player player) {
		// todo: move this to an InputAcceptor
		if (input instanceof EchoInput) {
			EchoInput echoInput = (EchoInput) input;
			fireEvent(new Echo(echoInput, (EchoPlayer) player));
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
