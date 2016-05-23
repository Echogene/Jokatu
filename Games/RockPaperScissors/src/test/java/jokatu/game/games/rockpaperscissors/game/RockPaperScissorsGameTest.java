package jokatu.game.games.rockpaperscissors.game;

import jokatu.game.GameID;
import jokatu.game.event.GameEvent;
import jokatu.game.event.StageOverEvent;
import jokatu.game.event.StatusUpdateEvent;
import jokatu.game.exception.GameException;
import jokatu.game.games.rockpaperscissors.input.RockPaperScissorsInput;
import jokatu.game.input.UnacceptableInputException;
import jokatu.game.joining.GameFullException;
import jokatu.game.joining.JoinInput;
import jokatu.game.joining.PlayerAlreadyJoinedException;
import jokatu.game.joining.PlayerJoinedEvent;
import jokatu.game.player.StandardPlayer;
import jokatu.game.result.PlayerResult;
import ophelia.collections.list.UnmodifiableList;
import ophelia.exceptions.CollectedException;
import ophelia.exceptions.StackedException;
import ophelia.util.StreamUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static jokatu.game.games.rockpaperscissors.game.RockPaperScissors.PAPER;
import static jokatu.game.games.rockpaperscissors.game.RockPaperScissors.ROCK;
import static ophelia.collections.matchers.IsCollectionWithSize.hasSize;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.fail;

public class RockPaperScissorsGameTest {

	private RockPaperScissorsGame game;
	private StandardPlayer player1;
	private StandardPlayer player2;

	@Before
	public void setUp() throws Exception {
		game = new RockPaperScissorsGame(new GameID(0L));
		// Start the game.
		game.advanceStage();
		player1 = new StandardPlayer("Player 1");
		player2 = new StandardPlayer("Player 2");
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

		assertThat(events, hasItem(instanceOf(StageOverEvent.class)));
	}

	@Test
	public void should_fail_to_add_third_player() throws Exception {

		game.accept(new JoinInput(), player1);
		game.accept(new JoinInput(), player2);
		assertThat(game.getPlayers(), hasSize(2));

		List<GameEvent> events = new ArrayList<>();
		game.observe(events::add);

		try {
			game.accept(new JoinInput(), new StandardPlayer("Player 3"));
			fail("Should not accept a third player's joining.");
		} catch (GameException e) {
			Exception cause = extractRootCause(e);
			assertThat(cause, instanceOf(GameFullException.class));
		}
		assertThat(events, is(empty()));
		assertThat(game.getPlayers(), hasSize(2));
	}

	@Test
	public void should_fail_to_add_player_who_tries_to_join_twice() throws Exception {

		game.accept(new JoinInput(), player1);
		assertThat(game.getPlayers(), hasSize(1));

		List<GameEvent> events = new ArrayList<>();
		game.observe(events::add);

		try {
			game.accept(new JoinInput(), player1);
			fail("Should not accept the same player joining twice.");
		} catch (GameException e) {
			Exception cause = extractRootCause(e);
			assertThat(cause, instanceOf(PlayerAlreadyJoinedException.class));
		}
		assertThat(events, is(empty()));
		assertThat(game.getPlayers(), hasSize(1));
	}

	@Test
	public void should_be_accept_input_from_a_player_after_joining_stage_is_over() throws Exception {
		game.accept(new JoinInput(), player1);
		game.accept(new JoinInput(), player2);

		List<GameEvent> events = new ArrayList<>();
		game.observe(events::add);

		game.advanceStage();

		assertThat(events, hasItem(instanceOf(StatusUpdateEvent.class)));
		StatusUpdateEvent statusUpdateEvent = getUniqueEventFromList(StatusUpdateEvent.class, events);
		assertThat(statusUpdateEvent.getMessage(), allOf(
				containsString("Waiting for inputs from"),
				containsString(player1.getName()),
				containsString(player2.getName())
		));
		events.clear();

		game.accept(new RockPaperScissorsInput(ROCK), player1);

		assertThat(events, hasItem(instanceOf(StatusUpdateEvent.class)));
		StatusUpdateEvent statusUpdateEvent2 = getUniqueEventFromList(StatusUpdateEvent.class, events);
		assertThat(statusUpdateEvent2.getMessage(), is("Waiting for input from Player 2."));
		events.clear();
	}

	@Test
	public void should_not_allow_player_to_change_mind() throws Exception {
		game.accept(new JoinInput(), player1);
		game.accept(new JoinInput(), player2);

		game.advanceStage();

		game.accept(new RockPaperScissorsInput(ROCK), player1);

		List<GameEvent> events = new ArrayList<>();
		game.observe(events::add);

		try {
			game.accept(new RockPaperScissorsInput(PAPER), player1);
			fail("Should not let player change mind.");
		} catch (GameException e) {
			Exception cause = extractRootCause(e);
			assertThat(cause, instanceOf(UnacceptableInputException.class));
		}
		assertThat(events, is(empty()));
	}

	@Test
	public void should_draw_game() throws Exception {
		game.accept(new JoinInput(), player1);
		game.accept(new JoinInput(), player2);

		game.advanceStage();

		game.accept(new RockPaperScissorsInput(ROCK), player1);

		List<GameEvent> events = new ArrayList<>();
		game.observe(events::add);

		game.accept(new RockPaperScissorsInput(ROCK), player2);

		assertThat(events, hasItem(instanceOf(StageOverEvent.class)));

		assertThat(events, hasItem(instanceOf(PlayerResult.class)));
		PlayerResult result = getUniqueEventFromList(PlayerResult.class, events);
		assertThat(result.getMessage(), allOf(
				containsString("DRAW"),
				containsString(player1.getName()),
				containsString(player2.getName())
		));
	}

	private Exception extractRootCause(GameException e) {
		assertThat(e.getCause(), instanceOf(StackedException.class));
		UnmodifiableList<Exception> causes = CollectedException.flatten((Exception) e.getCause());
		assertThat(causes, ophelia.collections.matchers.IsCollectionWithSize.hasSize(1));
		return causes.get(0);
	}

	private <E extends GameEvent> E getUniqueEventFromList(Class<E> eventClass, List<GameEvent> events) throws StackedException {
		return events.stream()
				.filter(eventClass::isInstance)
				.map(eventClass::cast)
				.collect(StreamUtils.findUnique())
				.returnOnSuccess()
				.throwAllFailures();
	}
}