package jokatu.game.games.noughtsandcrosses.input;

import jokatu.game.games.noughtsandcrosses.event.CellChosenEvent;
import jokatu.game.games.noughtsandcrosses.event.LineCompletedEvent;
import jokatu.game.games.noughtsandcrosses.player.NoughtsAndCrossesPlayer;
import jokatu.game.input.InputAcceptor;
import jokatu.game.status.StandardTextStatus;
import ophelia.collections.list.UnmodifiableList;
import ophelia.collections.set.HashSet;
import ophelia.collections.set.UnmodifiableSet;
import ophelia.collections.set.bounded.BoundedPair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static jokatu.game.games.noughtsandcrosses.input.NoughtOrCross.CROSS;
import static jokatu.game.games.noughtsandcrosses.input.NoughtOrCross.other;

public class NoughtsAndCrossesInputAcceptor extends InputAcceptor<NoughtsAndCrossesInput, NoughtsAndCrossesPlayer> {

	private final Map<Integer, NoughtOrCross> inputs = new HashMap<>();

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

	public NoughtsAndCrossesInputAcceptor(BoundedPair<NoughtsAndCrossesPlayer> players, StandardTextStatus status) {
		super();
	}

	@Override
	protected Class<NoughtsAndCrossesInput> getInputClass() {
		return NoughtsAndCrossesInput.class;
	}

	@Override
	protected Class<NoughtsAndCrossesPlayer> getPlayerClass() {
		return NoughtsAndCrossesPlayer.class;
	}

	@Override
	protected void acceptCastInputAndPlayer(NoughtsAndCrossesInput input, NoughtsAndCrossesPlayer inputter) throws Exception {
		Integer cell = input.getCellId();
		if (inputs.containsKey(cell)) {
			inputs.put(cell, other(inputs.get(cell)));
		} else {
			inputs.put(cell, CROSS);
		}
		fireEvent(new CellChosenEvent(cell, inputs.get(cell)));
		fireLineCompletedEvents();
	}

	private void fireLineCompletedEvents() {
		stream(NoughtOrCross.values()).forEach(this::fireLineCompletedEventsForPlayer);
	}

	private void fireLineCompletedEventsForPlayer(NoughtOrCross noughtOrCross) {
		Set<Integer> cellsForPlayer = getCellsForPlayer(noughtOrCross);
		LINES.stream()
				.filter(line -> line.stream().allMatch(cellsForPlayer::contains))
				.map(LineCompletedEvent::new)
				.forEach(this::fireEvent);
	}

	private Set<Integer> getCellsForPlayer(NoughtOrCross noughtOrCross) {
		return inputs.entrySet().stream()
				.filter(entry -> entry.getValue() == noughtOrCross)
				.map(Map.Entry::getKey)
				.collect(Collectors.toSet());
	}
}
