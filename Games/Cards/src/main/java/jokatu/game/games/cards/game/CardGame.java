package jokatu.game.games.cards.game;

import jokatu.game.Game;
import jokatu.game.GameID;
import jokatu.game.games.cards.player.CardPlayer;
import ophelia.collections.BaseCollection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static ophelia.collections.set.EmptySet.emptySet;

public class CardGame extends Game<CardPlayer> {

	public static final String CARDS = "Cards";

	CardGame(GameID identifier) {
		super(identifier);
	}

	@NotNull
	@Override
	public String getGameName() {
		return CARDS;
	}

	@Nullable
	@Override
	public CardPlayer getPlayerByName(@NotNull String name) {
		return null;
	}

	@Override
	public void advanceStageInner() {
		currentStage = new CardStage();
	}

	@Override
	public BaseCollection<CardPlayer> getPlayers() {
		return emptySet();
	}
}
