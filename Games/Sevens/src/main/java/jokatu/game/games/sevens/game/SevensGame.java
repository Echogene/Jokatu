package jokatu.game.games.sevens.game;

import jokatu.game.Game;
import jokatu.game.GameID;
import jokatu.game.cards.Card;
import jokatu.game.cards.Suit;
import jokatu.game.games.sevens.player.SevensPlayer;
import jokatu.game.stage.GameOverStage;
import jokatu.game.stage.MinAndMaxJoiningStage;
import jokatu.game.stage.machine.SequentialStageMachine;
import jokatu.game.status.StandardTextStatus;
import ophelia.collections.set.UnmodifiableSet;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class SevensGame extends Game<SevensPlayer> {

	public static final String SEVENS = "Sevens";

	private final StandardTextStatus status = new StandardTextStatus();
	private final Map<Suit, TreeSet<Card>> playedCards = new HashMap<Suit, TreeSet<Card>>() {{
		Arrays.stream(Suit.values())
				.forEach(suit -> put(suit, new TreeSet<>((c1, c2) -> c1.getRank().compareTo(c2.getRank()))));
	}};

	SevensGame(GameID identifier) {
		super(identifier);

		stageMachine = new SequentialStageMachine(
				() -> new MinAndMaxJoiningStage<>(SevensPlayer.class, players, 3, 7, status),
				() -> new SevensStage(players, status, playedCards),
				() -> new GameOverStage(status)
		);

		status.observe(this::fireEvent);
	}

	@NotNull
	@Override
	public String getGameName() {
		return SEVENS;
	}

	public void register(SevensPlayer player) {
		player.observe(this::fireEvent);
	}

	public UnmodifiableSet<Card> getCardsOfSuitPlayed(Suit suit) {
		return new UnmodifiableSet<>(playedCards.get(suit));
	}
}
