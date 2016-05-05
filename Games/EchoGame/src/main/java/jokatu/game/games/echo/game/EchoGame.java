package jokatu.game.games.echo.game;

import jokatu.game.Game;
import jokatu.game.GameID;
import jokatu.game.Stage;
import jokatu.game.event.StatusUpdateEvent;
import jokatu.game.games.echo.player.EchoPlayer;
import jokatu.game.status.Status;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class EchoGame extends Game<EchoPlayer> {

	public static final String ECHO = "Echo";

	private final EchoStage stage = new EchoStage();

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
		stage.observe(this::fireEvent);
	}

	@NotNull
	@Override
	protected Stage getCurrentStage() {
		return stage;
	}

	@NotNull
	@Override
	public String getGameName() {
		return ECHO;
	}

	@Nullable
	@Override
	public EchoPlayer getPlayerByName(@NotNull String name) {
		return null;
	}

	@NotNull
	@Override
	public Status getStatus() {
		return () -> "The time is: " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
	}

	@Override
	public void advanceStage() {
		// Do nothing.
	}
}
