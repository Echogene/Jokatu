package jokatu.game.games.uzta.game;

import jokatu.game.Game;
import jokatu.game.GameID;
import jokatu.game.player.StandardPlayer;
import jokatu.game.stage.GameOverStage;
import jokatu.game.status.StandardTextStatus;
import org.jetbrains.annotations.NotNull;

public class Uzta extends Game<StandardPlayer> {

	public static final String UZTA = "Uzta";

	private final StandardTextStatus status = new StandardTextStatus();

	Uzta(GameID identifier) {
		super(identifier);

		status.observe(this::fireEvent);
	}

	@NotNull
	@Override
	public String getGameName() {
		return UZTA;
	}

	@Override
	public void advanceStageInner() {
		currentStage = new GameOverStage(status);
	}
}
