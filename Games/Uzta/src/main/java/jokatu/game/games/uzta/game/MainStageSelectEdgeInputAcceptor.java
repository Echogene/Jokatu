package jokatu.game.games.uzta.game;

import jokatu.game.event.PublicGameEvent;
import jokatu.game.games.uzta.graph.LineSegment;
import jokatu.game.games.uzta.graph.Node;
import jokatu.game.games.uzta.graph.NodeType;
import jokatu.game.games.uzta.graph.UztaGraph;
import jokatu.game.games.uzta.input.SelectEdgeInput;
import jokatu.game.games.uzta.player.UztaPlayer;
import jokatu.game.input.UnacceptableInputException;
import ophelia.collections.bag.BaseIntegerBag;
import ophelia.collections.bag.HashBag;
import ophelia.collections.bag.ModifiableIntegerBag;
import ophelia.tuple.Pair;
import ophelia.util.MapUtils;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.emptySet;
import static jokatu.game.games.uzta.game.Uzta.DICE_SIZE;
import static ophelia.util.FunctionUtils.notNull;

public class MainStageSelectEdgeInputAcceptor extends AbstractSelectEdgeInputAcceptor {

	private final Map<Node, Set<LineSegment>> nodeToOwningEdges;
	private final Random die;

	MainStageSelectEdgeInputAcceptor(@NotNull UztaGraph graph, @NotNull Map<String, UztaPlayer> players) {
		super(graph, players);

		this.players.forEach(player -> player.observe(this::fireEvent));

		nodeToOwningEdges = new HashMap<>(); 
		
		graph.getEdges().stream()
				.forEach(edge -> edge.forEach(node -> MapUtils.updateSetBasedMap(nodeToOwningEdges, node, edge)));

		die = new Random();
	}

	void startNextTurn() {
		turnManager.next();
		roll();
	}

	void distributeStartingResources() {
		graph.getEdges().stream()
				.filter(edge -> edge.getOwner() != null)
				.forEach(this::giveStartingResourcesForEdge);
	}

	private void giveStartingResourcesForEdge(LineSegment edge) {
		UztaPlayer owner = edge.getOwner();
		assert owner != null;
		edge.forEach(node -> giveResourceFromNode(owner, node));
	}

	private void giveResourceFromNode(@NotNull UztaPlayer owner, @NotNull Node node) {
		owner.giveResources(node.getType(), 1);
	}

	private void roll() {
		int roll = 1 + die.nextInt(DICE_SIZE);
		UztaPlayer currentPlayer = turnManager.getCurrentPlayer();
		assert currentPlayer != null;
		fireEvent((PublicGameEvent) () -> MessageFormat.format("{0} rolled {1}", currentPlayer.getName(), roll));

		graph.getNodes().stream()
				.filter(node -> node.getValues().contains(roll))
				.forEach(node -> {
					Set<LineSegment> edges = nodeToOwningEdges.getOrDefault(node, emptySet());
					edges.stream()
							.map(LineSegment::getOwner)
							.filter(notNull())
							.forEach(owner -> giveResourceFromNode(owner, node));
				});
	}

	@Override
	protected void acceptCastInputAndPlayer(@NotNull SelectEdgeInput input, @NotNull UztaPlayer inputter) throws Exception {
		LineSegment edge = getUnownedLineSegment(input, inputter);

		BaseIntegerBag<NodeType> edgeCost = getCost(edge);
		BaseIntegerBag<NodeType> resourcesLeft = inputter.getResourcesLeftAfter(edgeCost);
		if (resourcesLeft.isLacking()) {
			throw new UnacceptableInputException(
					"You can''t afford that edge.  You still need {0}.",
					resourcesLeft.stream()
							.filter(pair -> pair.getRight() < 0)
							.map(this::presentNeededResources)
							.collect(Collectors.joining(", "))
			);
		}

		setOwner(edge, inputter);

		startNextTurn();
	}

	@NotNull
	private String presentNeededResources(Pair<NodeType, Integer> pair) {
		Integer numberNeeded = -pair.getRight();
		return pair.getLeft().getNumber(numberNeeded);
	}

	private BaseIntegerBag<NodeType> getCost(@NotNull LineSegment edge) {
		ModifiableIntegerBag<NodeType> bag = new HashBag<>();
		edge.forEach(node -> bag.modifyNumberOf(node.getType(), node.getValues().size()));
		Arrays.stream(NodeType.values()).forEach(bag::addOne);
		return bag;
	}
}
