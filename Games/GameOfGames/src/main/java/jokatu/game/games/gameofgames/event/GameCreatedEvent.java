package jokatu.game.games.gameofgames.event;

import jokatu.game.event.PublicGameEvent;
import jokatu.game.games.gameofgames.player.GameOfGamesPlayer;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;


public class GameCreatedEvent implements PublicGameEvent {

	private final GameOfGamesPlayer player;
	private final String gameName;

	public GameCreatedEvent(GameOfGamesPlayer player, String gameName) {
		this.player = player;
		this.gameName = gameName;
	}

	@NotNull
	@Override
	public String getMessage() {
		return MessageFormat.format("{0} created a {1}", player.getName(), gameName);
	}

	public String getGameName() {
		return gameName;
	}

	public GameOfGamesPlayer getPlayer() {
		return player;
	}
}
