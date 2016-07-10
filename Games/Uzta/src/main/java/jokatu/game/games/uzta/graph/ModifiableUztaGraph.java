package jokatu.game.games.uzta.graph;

import ophelia.collections.list.ArrayList;
import ophelia.collections.set.HashSet;
import ophelia.graph.ModifiableBiGraph;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

public class ModifiableUztaGraph implements UztaGraph, ModifiableBiGraph<Node, LineSegment> {

	protected final ArrayList<Node> nodes;
	protected final HashSet<LineSegment> edges;

	public ModifiableUztaGraph(@NotNull List<Node> nodes, @NotNull Set<LineSegment> edges) {
		this.nodes = new ArrayList<>(nodes);
		this.edges = new HashSet<>(edges);
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
