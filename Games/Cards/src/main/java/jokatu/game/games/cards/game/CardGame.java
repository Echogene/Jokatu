package jokatu.game.games.cards.game;

import jokatu.game.Game;
import jokatu.game.GameID;
import jokatu.game.cards.Card;
import jokatu.game.cards.Suit;
import jokatu.game.games.cards.player.CardPlayer;
import jokatu.game.stage.GameOverStage;
import jokatu.game.stage.JoiningStage;
import jokatu.game.status.StandardTextStatus;
import ophelia.collections.BaseCollection;
import ophelia.collections.set.UnmodifiableSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class CardGame extends Game<CardPlayer> {

	public static final String CARDS = "Cards";

	private final Map<String, CardPlayer> players = new HashMap<>();

	private final StandardTextStatus status = new StandardTextStatus();
	private final Map<Suit, TreeSet<Card>> playedCards = new HashMap<Suit, TreeSet<Card>>() {{
		Arrays.stream(Suit.values())
				.forEach(suit -> put(suit, new TreeSet<>((c1, c2) -> c1.getRank().compareTo(c2.getRank()))));
	}};

	CardGame(GameID identifier) {
		super(identifier);

		status.observe(this::fireEvent);
	}

	@NotNull
	@Override
	public String getGameName() {
		return CARDS;
	}

	@Nullable
	@Override
	public CardPlayer getPlayerByName(@NotNull String name) {
		return players.get(name);
	}

	@Override
	public void advanceStageInner() {
		if (currentStage == null) {
			// todo: this should have a minimum and a maximum
			currentStage = new JoiningStage<>(CardPlayer.class, players, 1, status);

		} else if (currentStage instanceof JoiningStage) {
			currentStage = new CardStage(players, status, playedCards);

		} else {
			currentStage = new GameOverStage(status);
		}
	}

	@Override
	public BaseCollection<CardPlayer> getPlayers() {
		return new UnmodifiableSet<>(players.values());
	}

	public void register(CardPlayer player) {
		player.observe(this::fireEvent);
	}

	public UnmodifiableSet<Card> getCardsOfSuitPlayed(Suit suit) {
		return new UnmodifiableSet<>(playedCards.get(suit));
	}
}
