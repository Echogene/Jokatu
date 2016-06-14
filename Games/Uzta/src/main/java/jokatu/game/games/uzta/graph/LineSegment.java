package jokatu.game.games.uzta.graph;

import org.jetbrains.annotations.NotNull;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class LineSegment {

	private final Node node1;
	private final Node node2;

	public LineSegment(@NotNull Node node1, @NotNull Node node2) {
		this.node1 = node1;
		this.node2 = node2;
	}

	public double squareDistanceFrom(@NotNull Node point) {
		double squareLength = node1.squareDistanceFrom(node2);
		if (squareLength == 0) {
			return point.squareDistanceFrom(node1);
		}
		double projection = max(0, min(1, (
						(point.getX() - node1.getX()) * (node2.getX() - node1.getX())
						+ (point.getY() - node1.getY()) * (node2.getY() - node1.getY())
				) / squareLength
		));
		return point.squareDistanceFrom(
				node1.getX() + projection * (node2.getX() - node1.getX()),
				node1.getY() + projection * (node2.getY() - node1.getY())
		);
	}
}
