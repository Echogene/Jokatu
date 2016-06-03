package jokatu.game.games.noughtsandcrosses.game;

import jokatu.game.games.noughtsandcrosses.event.CellChosenEvent;
import jokatu.game.games.noughtsandcrosses.input.NoughtOrCross;
import jokatu.game.games.noughtsandcrosses.input.NoughtsAndCrossesInput;
import jokatu.game.games.noughtsandcrosses.player.NoughtsAndCrossesPlayer;
import jokatu.game.input.UnacceptableInputException;
import jokatu.game.result.PlayerResult;
import jokatu.game.stage.AnyEventSingleInputStage;
import jokatu.game.status.StandardTextStatus;
import jokatu.game.turn.TurnManager;
import ophelia.collections.list.UnmodifiableList;
import ophelia.collections.set.HashSet;
import ophelia.collections.set.UnmodifiableSet;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.text.MessageFormat.format;
import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.Collections.singleton;
import static jokatu.game.games.noughtsandcrosses.input.NoughtOrCross.CROSS;
import static jokatu.game.result.Result.DRAW;
import static jokatu.game.result.Result.WIN;

class InputStage extends AnyEventSingleInputStage<NoughtsAndCrossesInput, NoughtsAndCrossesPlayer> {

	private static final UnmodifiableSet<UnmodifiableList<Integer>> LINES;
	static {
		Set<UnmodifiableList<Integer>> lines = new HashSet<>();
		IntStream.range(0, 3)
				.map(i -> i * 3)
				.forEach(i -> lines.add(new UnmodifiableList<>(asList(i, i + 1, i + 2))));
		IntStream.range(0, 3)
				.forEach(i -> lines.add(new UnmodifiableList<>(asList(i, i + 3, i + 6))));
		lines.add(new UnmodifiableList<>(asList(0, 4, 8)));
		lines.add(new UnmodifiableList<>(asList(2, 4, 6)));
		LINES = new UnmodifiableSet<>(lines);
	}

	private final Map<Integer, NoughtOrCross> inputs = new HashMap<>();

	private final ArrayList<NoughtsAndCrossesPlayer> players;
	private TurnManager<NoughtsAndCrossesPlayer> turnManager;

	InputStage(Collection<NoughtsAndCrossesPlayer> players, StandardTextStatus status) {
		this.players = new ArrayList<>(players);

		// Crosses go first.
		NoughtsAndCrossesPlayer startingPlayer = players.stream()
				.filter(player -> player.getAllegiance() == CROSS)
				.findAny()
				.orElseThrow(() -> new RuntimeException("No player was aligned to crosses."));

		status.setText(format("Waiting for {0} to choose a cell.", startingPlayer));
		turnManager = new TurnManager<>(this.players, startingPlayer);
		turnManager.observe(e -> {
			status.setText(format("Waiting for {0} to choose a cell.", e.getNewPlayer()));
			// Forward the event.
			fireEvent(e);
		});
	}

	@NotNull
	@Override
	protected Class<NoughtsAndCrossesInput> getInputClass() {
		return NoughtsAndCrossesInput.class;
	}

	@NotNull
	@Override
	protected Class<NoughtsAndCrossesPlayer> getPlayerClass() {
		return NoughtsAndCrossesPlayer.class;
	}

	@Override
	protected void acceptCastInputAndPlayer(@NotNull NoughtsAndCrossesInput input, @NotNull NoughtsAndCrossesPlayer inputter) throws Exception {
		turnManager.assertCurrentPlayer(inputter);

		Integer cell = input.getCellId();
		if (inputs.containsKey(cell)) {
			throw new UnacceptableInputException("Cell {0} already contained a {1}.", cell, inputs.get(cell));
		}
		inputs.put(cell, inputter.getAllegiance());
		turnManager.next();

		List<Line> lines = getCompletedLines();
		fireEvent(new CellChosenEvent(cell, inputs.get(cell), lines));
		if (!lines.isEmpty()) {
			fireEvent(new PlayerResult(WIN, singleton(inputter)));
		} else if (inputs.size() == 9) {
			fireEvent(new PlayerResult(DRAW, players));
		}
	}

	private List<Line> getCompletedLines() {
		return stream(NoughtOrCross.values()).map(this::fireLineCompletedEventsForPlayer)
				.flatMap(List::stream)
				.map(Line::new)
				.collect(Collectors.toList());
	}

	private List<UnmodifiableList<Integer>> fireLineCompletedEventsForPlayer(NoughtOrCross noughtOrCross) {
		Set<Integer> cellsForPlayer = getCellsForPlayer(noughtOrCross);
		return LINES.stream()
				.filter(line -> line.stream().allMatch(cellsForPlayer::contains))
				.collect(Collectors.toList());
	}

	private Set<Integer> getCellsForPlayer(NoughtOrCross noughtOrCross) {
		return inputs.entrySet().stream()
				.filter(entry -> entry.getValue() == noughtOrCross)
				.map(Map.Entry::getKey)
				.collect(Collectors.toSet());
	}
}
