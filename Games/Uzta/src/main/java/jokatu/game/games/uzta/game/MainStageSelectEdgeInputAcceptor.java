package jokatu.game.games.uzta.game;

import jokatu.game.event.PublicGameEvent;
import jokatu.game.games.uzta.graph.LineSegment;
import jokatu.game.games.uzta.graph.NodeType;
import jokatu.game.games.uzta.graph.UztaGraph;
import jokatu.game.games.uzta.input.SelectEdgeInput;
import jokatu.game.games.uzta.player.UztaPlayer;
import jokatu.game.input.UnacceptableInputException;
import jokatu.game.turn.TurnManager;
import ophelia.collections.bag.BaseIntegerBag;
import ophelia.collections.bag.HashBag;
import ophelia.collections.bag.ModifiableIntegerBag;
import ophelia.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

import static jokatu.game.games.uzta.game.Uzta.DICE_SIZE;

public class MainStageSelectEdgeInputAcceptor extends AbstractSelectEdgeInputAcceptor {

	private final ResourceDistributor resourceDistributor;
	private final Random die;

	MainStageSelectEdgeInputAcceptor(@NotNull UztaGraph graph, @NotNull TurnManager<UztaPlayer> turnManager, @NotNull ResourceDistributor resourceDistributor) {
		super(graph, turnManager);
		this.resourceDistributor = resourceDistributor;

		turnManager.observe(e -> roll());

		die = new Random();
	}

	private void roll() {
		int roll = 1 + die.nextInt(DICE_SIZE);
		UztaPlayer currentPlayer = turnManager.getCurrentPlayer();
		assert currentPlayer != null;
		fireEvent((PublicGameEvent) () -> MessageFormat.format("{0} rolled {1}", currentPlayer.getName(), roll));

		resourceDistributor.distributeResourcesForRoll(roll);
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
