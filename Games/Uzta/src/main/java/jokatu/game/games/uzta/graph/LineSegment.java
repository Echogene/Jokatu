package jokatu.game.games.uzta.graph;

import ophelia.collections.pair.UnorderedPair;
import org.jetbrains.annotations.NotNull;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class LineSegment extends UnorderedPair<Node> {

	public LineSegment(@NotNull Node first, @NotNull Node second) {
		super(first, second);
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
}
