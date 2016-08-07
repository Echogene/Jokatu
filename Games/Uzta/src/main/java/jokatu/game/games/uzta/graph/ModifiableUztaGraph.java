package jokatu.game.games.uzta.graph;

import ophelia.collections.list.ArrayList;
import ophelia.collections.set.HashSet;
import ophelia.graph.ModifiableBiGraph;
import org.jetbrains.annotations.NotNull;

public class ModifiableUztaGraph implements UztaGraph, ModifiableBiGraph<Node, LineSegment> {

	protected final ArrayList<Node> nodes;
	protected final HashSet<LineSegment> edges;

	public ModifiableUztaGraph() {
		this.nodes = new ArrayList<>();
		this.edges = new HashSet<>();
	}

	@NotNull
	@Override
	public HashSet<LineSegment> getEdges() {
		return edges;
	}

	@NotNull
	@Override
	public ArrayList<Node> getNodes() {
		return nodes;
	}
}
