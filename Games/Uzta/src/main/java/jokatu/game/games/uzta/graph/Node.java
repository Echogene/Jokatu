package jokatu.game.games.uzta.graph;

import java.util.Comparator;

import static java.util.Comparator.comparingDouble;

public class Node {
	private final String id;
	private final double x;
	private final double y;

	public Node(String id, double x, double y) {
		this.id = id;
		this.x = x;
		this.y = y;
	}

	public String getId() {
		return id;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double distanceFrom(Node other) {
		return Math.sqrt(squareDistanceFrom(other));
	}

	public double squareDistanceFrom(Node other) {
		double δx = x - other.x;
		double δy = y - other.y;
		return δx * δx + δy * δy;
	}

	public Comparator<Node> nearest() {
		return comparingDouble(this::squareDistanceFrom);
	}
}
