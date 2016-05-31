package jokatu.game.games.noughtsandcrosses.game;

import jokatu.game.event.StageOverEvent;
import jokatu.game.games.noughtsandcrosses.event.CellChosenEvent;
import jokatu.game.games.noughtsandcrosses.input.NoughtOrCross;
import jokatu.game.games.noughtsandcrosses.input.NoughtsAndCrossesInput;
import jokatu.game.games.noughtsandcrosses.player.NoughtsAndCrossesPlayer;
import jokatu.game.input.AnyEventInputAcceptor;
import jokatu.game.input.UnacceptableInputException;
import jokatu.game.result.PlayerResult;
import jokatu.game.status.StandardTextStatus;
import ophelia.collections.list.UnmodifiableList;
import ophelia.collections.set.HashSet;
import ophelia.collections.set.UnmodifiableSet;
import ophelia.collections.set.bounded.BoundedPair;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.Collections.singleton;
import static jokatu.game.games.noughtsandcrosses.input.NoughtOrCross.CROSS;
import static jokatu.game.result.Result.DRAW;
import static jokatu.game.result.Result.WIN;

class InputStage extends AnyEventInputAcceptor<NoughtsAndCrossesInput, NoughtsAndCrossesPlayer> {

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

	private final BoundedPair<NoughtsAndCrossesPlayer> players;
	private final StandardTextStatus status;

	private NoughtsAndCrossesPlayer currentPlayer;

	InputStage(Collection<NoughtsAndCrossesPlayer> players, StandardTextStatus status) {
		this.players = new BoundedPair<>(players);
		this.status = status;

		// Crosses go first.
		currentPlayer = players.stream()
				.filter(player -> player.getAllegiance() == CROSS)
				.findAny()
				.orElseThrow(() -> new RuntimeException("No player was aligned to crosses."));

		this.status.setText("Waiting for input from {0}.", currentPlayer);
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
		Integer cell = input.getCellId();
		if (inputs.containsKey(cell)) {
			throw new UnacceptableInputException("Cell {0} already contained a {1}.", cell, inputs.get(cell));

		} else if (inputter != currentPlayer) {
			throw new UnacceptableInputException("It''s not your turn!  Wait for {0}.", currentPlayer);

		} else {
			inputs.put(cell, inputter.getAllegiance());
			currentPlayer = players.getOther(currentPlayer);
			this.status.setText("Waiting for input from {0}.", currentPlayer);
		}
		List<Line> lines = getCompletedLines();
		fireEvent(new CellChosenEvent(cell, inputs.get(cell), lines));
		if (!lines.isEmpty()) {
			fireEvent(new PlayerResult(WIN, singleton(inputter)));
			fireEvent(new StageOverEvent());
		} else if (inputs.size() == 9) {
			fireEvent(new PlayerResult(DRAW, asList(players.getFirst(), players.getSecond())));
			fireEvent(new StageOverEvent());
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
