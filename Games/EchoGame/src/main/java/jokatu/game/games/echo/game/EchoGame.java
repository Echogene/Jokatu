package jokatu.game.games.echo.game;

import jokatu.game.Game;
import jokatu.game.GameID;
import jokatu.game.event.StatusUpdateEvent;
import jokatu.game.games.echo.input.EchoInputAcceptor;
import jokatu.game.games.echo.player.EchoPlayer;
import jokatu.game.input.Input;
import jokatu.game.input.InputAcceptor;
import jokatu.game.player.Player;
import jokatu.game.status.Status;
import ophelia.collections.BaseCollection;
import ophelia.collections.set.Singleton;
import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

import static ophelia.collections.set.EmptySet.emptySet;

public class EchoGame extends Game<EchoPlayer> {

	public static final String ECHO = "Echo";
	private final Singleton<InputAcceptor<? extends Input, ? extends Player>> inputAcceptors = new Singleton<>(
			new EchoInputAcceptor()
	);

	EchoGame(GameID identifier) {
		super(identifier);
		new Timer().scheduleAtFixedRate(
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
	protected BaseCollection<InputAcceptor<? extends Input, ? extends Player>> getInputAcceptors() {
		return inputAcceptors;
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
}
