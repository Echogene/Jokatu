package jokatu.game.games.rockpaperscissors.game;

import jokatu.game.GameID;
import jokatu.game.event.GameEvent;
import jokatu.game.exception.GameException;
import jokatu.game.games.rockpaperscissors.player.RockPaperScissorsPlayer;
import jokatu.game.joining.GameFullException;
import jokatu.game.joining.JoinInput;
import jokatu.game.joining.PlayerJoinedEvent;
import ophelia.collections.list.UnmodifiableList;
import ophelia.exceptions.CollectedException;
import ophelia.exceptions.StackedException;
import ophelia.util.StreamUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static ophelia.collections.matchers.IsCollectionWithSize.hasSize;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;

public class RockPaperScissorsGameTest {

	private RockPaperScissorsGame game;
	private RockPaperScissorsPlayer player1;
	private RockPaperScissorsPlayer player2;

	@Before
	public void setUp() throws Exception {
		game = new RockPaperScissorsGame(new GameID(0L));
		player1 = new RockPaperScissorsPlayer("Player 1");
		player2 = new RockPaperScissorsPlayer("Player 2");
	}

	@Test
	public void should_fire_player_joined_event_when_they_join_as_the_first_player() throws Exception {

		assertThat(game.getPlayers(), hasSize(0));

		List<GameEvent> events = new ArrayList<>();
		game.observe(events::add);

		game.accept(new JoinInput(), player1);

		assertThat(game.getPlayers(), hasSize(1));
		assertThat(events, hasItem(instanceOf(PlayerJoinedEvent.class)));
		PlayerJoinedEvent playerJoinedEvent = getUniqueEventFromList(PlayerJoinedEvent.class, events);
		assertThat(playerJoinedEvent.getMessage(), is("Player 1 joined the game."));
	}

	@Test
	public void should_fire_player_joined_event_when_they_join_as_the_second_player() throws Exception {

		game.accept(new JoinInput(), player1);
		assertThat(game.getPlayers(), hasSize(1));

		List<GameEvent> events = new ArrayList<>();
		game.observe(events::add);

		game.accept(new JoinInput(), player2);

		assertThat(game.getPlayers(), hasSize(2));
		assertThat(events, hasItem(instanceOf(PlayerJoinedEvent.class)));
		PlayerJoinedEvent playerJoinedEvent = getUniqueEventFromList(PlayerJoinedEvent.class, events);
		assertThat(playerJoinedEvent.getMessage(), is("Player 2 joined the game."));
	}

	@Test
	public void should_fail_to_add_third_player() throws Exception {

		game.accept(new JoinInput(), player1);
		game.accept(new JoinInput(), player2);
		assertThat(game.getPlayers(), hasSize(2));

		List<GameEvent> events = new ArrayList<>();
		game.observe(events::add);

		try {
			game.accept(new JoinInput(), new RockPaperScissorsPlayer("Player 3"));
		} catch (GameException e) {
			assertThat(events, is(empty()));
			assertThat(e.getCause(), instanceOf(StackedException.class));
			UnmodifiableList<Exception> causes = CollectedException.flatten((Exception) e.getCause());
			assertThat(causes, hasSize(1));
			Exception cause = causes.get(0);
			assertThat(cause, instanceOf(GameFullException.class));
		}
	}

	private PlayerJoinedEvent getUniqueEventFromList(Class<PlayerJoinedEvent> eventClass, List<GameEvent> events) throws StackedException {
		return events.stream()
				.filter(eventClass::isInstance)
				.map(eventClass::cast)
				.collect(StreamUtils.findUnique())
				.returnOnSuccess()
				.throwAllFailures();
	}
}