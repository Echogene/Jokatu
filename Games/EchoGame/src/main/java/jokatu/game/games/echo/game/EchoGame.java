package jokatu.game.games.echo.game;

import jokatu.game.Game;
import jokatu.game.GameID;
import jokatu.game.event.StatusUpdateEvent;
import jokatu.game.games.echo.player.EchoPlayer;
import ophelia.collections.BaseCollection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Timer;
import java.util.TimerTask;

import static java.time.LocalTime.now;
import static java.time.format.DateTimeFormatter.ofPattern;
import static ophelia.collections.set.EmptySet.emptySet;

public class EchoGame extends Game<EchoPlayer> {

	public static final String ECHO = "Echo";

	EchoGame(GameID identifier) {
		super(identifier);
		new Timer().scheduleAtFixedRate(
				new TimerTask() {
					@Override
					public void run() {
						fireEvent((StatusUpdateEvent) () -> () -> "The time is: " + now().format(ofPattern("HH:mm:ss")));
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

	@Nullable
	@Override
	public EchoPlayer getPlayerByName(@NotNull String name) {
		return null;
	}

	@Override
	public void advanceStageInner() {
		currentStage = new EchoStage();
	}

	@Override
	public BaseCollection<EchoPlayer> getPlayers() {
		return emptySet();
	}
}
