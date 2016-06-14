package jokatu.game.games.uzta.graph;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

import static java.util.Comparator.comparingDouble;

public class Node {
	private final String id;
	private final double x;
	private final double y;

	public Node(@NotNull String id, double x, double y) {
		this.id = id;
		this.x = x;
		this.y = y;
	}

	@NotNull
	public String getId() {
		return id;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double distanceFrom(@NotNull Node other) {
		return Math.sqrt(squareDistanceFrom(other));
	}

	public double squareDistanceFrom(@NotNull Node other) {
		return squareDistanceFrom(other.x, other.y);
	}

	public double squareDistanceFrom(double x, double y) {
		double δx = this.x - x;
		double δy = this.y - y;
		return δx * δx + δy * δy;
	}

	@NotNull
	public Comparator<Node> nearest() {
		return comparingDouble(this::squareDistanceFrom);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Node node = (Node) o;

		return id.equals(node.id);

	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
