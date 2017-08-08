package jokatu.game.games.uzta.graph;

import ophelia.collections.set.HashSet;
import org.ejml.simple.SimpleMatrix;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class Trigon {

	private final Node Δ1;
	private final Node Δ2;
	private final Node Δ3;
	private final Set<LineSegment> edges;
	private final Set<Node> nodes;

	public Trigon(@NotNull Node Δ1, @NotNull Node Δ2, @NotNull Node Δ3) {

		double anticlockwise = (Δ2.getX() - Δ1.getX()) * (Δ3.getY() - Δ1.getY())
		                            - (Δ2.getY() - Δ1.getY()) * (Δ3.getX() - Δ1.getX());
		this.Δ1 = Δ1;
		if (anticlockwise > 0) {
			this.Δ2 = Δ2;
			this.Δ3 = Δ3;
		} else {
			this.Δ2 = Δ3;
			this.Δ3 = Δ2;
		}

		nodes = new HashSet<Node>() {{
			add(Δ1);
			add(Δ2);
			add(Δ3);
		}};

		edges = new HashSet<LineSegment>() {{
			add(new LineSegment(Δ1, Δ2));
			add(new LineSegment(Δ2, Δ3));
			add(new LineSegment(Δ3, Δ1));
		}};
	}

	public Trigon(@NotNull LineSegment edge, @NotNull Node Δ3) {
		this(edge.getFirst(), edge.getSecond(), Δ3);
	}

	public Set<LineSegment> getEdges() {
		return edges;
	}

	public Set<Node> getNodes() {
		return nodes;
	}

	public boolean circumcircleContains(@NotNull Node node) {
		SimpleMatrix circumcircle = new SimpleMatrix(4, 4, true,
				Δ1.getX(), Δ1.getY(), Δ1.getX() * Δ1.getX() + Δ1.getY() * Δ1.getY(), 1,
				Δ2.getX(), Δ2.getY(), Δ2.getX() * Δ2.getX() + Δ2.getY() * Δ2.getY(), 1,
				Δ3.getX(), Δ3.getY(), Δ3.getX() * Δ3.getX() + Δ3.getY() * Δ3.getY(), 1,
				node.getX(), node.getY(), node.getX() * node.getX() + node.getY() * node.getY(), 1
		);
		return circumcircle.determinant() > 0;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Trigon trigon = (Trigon) o;

		if (!Δ1.equals(trigon.Δ1)) return false;
		if (!Δ2.equals(trigon.Δ2)) return false;
		return Δ3.equals(trigon.Δ3);

	}

	@Override
	public int hashCode() {
		int result = Δ1.hashCode();
		result = 31 * result + Δ2.hashCode();
		result = 31 * result + Δ3.hashCode();
		return result;
	}
}
