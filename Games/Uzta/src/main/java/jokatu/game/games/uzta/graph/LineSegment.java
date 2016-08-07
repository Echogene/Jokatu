package jokatu.game.games.uzta.graph;

import com.fasterxml.jackson.annotation.JsonValue;
import jokatu.game.games.uzta.player.UztaPlayer;
import ophelia.collections.iterator.StandardIterator;
import ophelia.collections.pair.Pair;
import ophelia.collections.pair.UnorderedPair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class LineSegment implements Pair<Node> {

	private final Node first;
	private final Node second;
	private final UnorderedPair<Node> nodes;

	@Nullable
	private UztaPlayer owner;

	public LineSegment(@NotNull Node first, @NotNull Node second) {
		this.first = first;
		this.second = second;
		nodes = new UnorderedPair<>(first, second);
	}

	@NotNull
	public Node getFirst() {
		return first;
	}

	@NotNull
	public Node getSecond() {
		return second;
	}

	@JsonValue
	Map<String, Object> getJson() {
		return new HashMap<String, Object>() {{
			put("nodes", nodes);
			if (owner != null) {
				put("colour", owner.getColour());
			}
		}};
	}

	public void setOwner(@Nullable UztaPlayer owner) {
		this.owner = owner;
	}

	@Nullable
	public UztaPlayer getOwner() {
		return owner;
	}

	public double squareDistanceFrom(@NotNull Node point) {
		double squareLength = first.squareDistanceFrom(second);
		if (squareLength == 0) {
			return point.squareDistanceFrom(first);
		}
		double projection = max(0, min(1, (
						(point.getX() - first.getX()) * (second.getX() - first.getX())
						+ (point.getY() - first.getY()) * (second.getY() - first.getY())
				) / squareLength
		));
		return point.squareDistanceFrom(
				first.getX() + projection * (second.getX() - first.getX()),
				first.getY() + projection * (second.getY() - first.getY())
		);
	}

	@NotNull
	@Override
	public Object[] toArray() {
		return nodes.toArray();
	}

	@NotNull
	@Override
	public <T> T[] toArray(T[] a) {
		return nodes.toArray(a);
	}

	@Override
	public boolean contains(Object o) {
		return nodes.contains(o);
	}

	@NotNull
	@Override
	public StandardIterator<Node> iterator() {
		return nodes.iterator();
	}

	@Override
	public void forEach(Consumer<? super Node> consumer) {
		nodes.forEach(consumer);
	}

	@Override
	public Spliterator<Node> spliterator() {
		return nodes.spliterator();
	}

	@Override
	public Stream<Node> stream() {
		return nodes.stream();
	}

	@Override
	public Stream<Node> parallelStream() {
		return nodes.parallelStream();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		LineSegment that = (LineSegment) o;

		if (!nodes.equals(that.nodes)) return false;
		return owner != null ? owner.equals(that.owner) : that.owner == null;

	}

	@Override
	public int hashCode() {
		int result = nodes.hashCode();
		result = 31 * result + (owner != null ? owner.hashCode() : 0);
		return result;
	}
}
