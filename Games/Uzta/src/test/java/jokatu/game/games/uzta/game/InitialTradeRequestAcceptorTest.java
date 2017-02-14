package jokatu.game.games.uzta.game;

import jokatu.game.games.uzta.graph.NodeType;
import jokatu.game.games.uzta.input.SupplyTradeRequest;
import jokatu.game.games.uzta.player.UztaPlayer;
import jokatu.game.input.UnacceptableInputException;
import jokatu.game.player.Player;
import ophelia.collections.bag.HashBag;
import ophelia.exceptions.voidmaybe.VoidMaybe;
import org.junit.Before;
import org.junit.Test;

import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static jokatu.game.games.uzta.graph.NodeType.CIRCLE;
import static jokatu.game.games.uzta.graph.NodeType.SQUARE;
import static ophelia.exceptions.CollectedExceptionMatchers.hasException;
import static ophelia.exceptions.ExceptionMatchers.hasMessage;
import static ophelia.exceptions.voidmaybe.VoidMaybeMatchers.failure;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.Is.is;

public class InitialTradeRequestAcceptorTest {

	private UztaPlayer player;
	private UztaPlayer player2;
	private UztaPlayer player3;

	private InitialTradeRequestAcceptor initialTradeRequestAcceptor;

	@Before
	public void setUp() throws Exception {
		player = new UztaPlayer("Player");
		player2 = new UztaPlayer("Player 2");
		player3 = new UztaPlayer("Player 3");

		initialTradeRequestAcceptor = new InitialTradeRequestAcceptor(
				Stream.of(player, player2, player3)
						.collect(toMap(Player::getName, identity()))
		);
	}

	@Test
	public void should_accept_valid_supply_trade() throws Exception {
		player.giveResources(HashBag.of(3, SQUARE));

		SupplyTradeRequest supplyTradeRequest = new SupplyTradeRequest(new HashBag<NodeType>() {{
			modifyNumberOf(SQUARE, -3);
			addOne(CIRCLE);
		}});
		initialTradeRequestAcceptor.acceptSupplyRequest(supplyTradeRequest, player);
		assertThat(player.getNumberOfType(SQUARE), is(0));
		assertThat(player.getNumberOfType(CIRCLE), is(1));
	}

	@Test
	public void should_not_accept_supply_trade_when_trader_has_not_enough_resources() throws Exception {
		player.giveResources(HashBag.of(2, SQUARE));

		SupplyTradeRequest supplyTradeRequest = new SupplyTradeRequest(new HashBag<NodeType>() {{
			modifyNumberOf(SQUARE, -3);
			addOne(CIRCLE);
		}});
		VoidMaybe voidMaybe = VoidMaybe.wrap(() -> initialTradeRequestAcceptor.acceptSupplyRequest(
				supplyTradeRequest,
				player
		)).get();
		assertThat(voidMaybe, failure(hasException(is(instanceOf(UnacceptableInputException.class)))));
		assertThat(voidMaybe, failure(hasException(hasMessage("You don't have enough resources to give 3 squares.  You still need 1 square."))));
		assertThat(player.getNumberOfType(SQUARE), is(2));
		assertThat(player.getNumberOfType(CIRCLE), is(0));
	}

	@Test
	public void should_not_accept_supply_trade_when_trader_is_requesting_too_much() throws Exception {
		player.giveResources(HashBag.of(3, SQUARE));

		SupplyTradeRequest supplyTradeRequest = new SupplyTradeRequest(new HashBag<NodeType>() {{
			modifyNumberOf(SQUARE, -3);
			modifyNumberOf(CIRCLE, 2);
		}});
		VoidMaybe voidMaybe = VoidMaybe.wrap(() -> initialTradeRequestAcceptor.acceptSupplyRequest(
				supplyTradeRequest,
				player
		)).get();
		assertThat(voidMaybe, failure(hasException(is(instanceOf(UnacceptableInputException.class)))));
		assertThat(voidMaybe, failure(hasException(hasMessage("The trade did not balance.  You can't get 2 circles by giving 3 squares."))));
		assertThat(player.getNumberOfType(SQUARE), is(3));
		assertThat(player.getNumberOfType(CIRCLE), is(0));
	}

	@Test
	public void should_not_accept_supply_trade_when_trader_is_not_requesting_enough() throws Exception {
		player.giveResources(HashBag.of(3, SQUARE));

		SupplyTradeRequest supplyTradeRequest = new SupplyTradeRequest(new HashBag<NodeType>() {{
			modifyNumberOf(SQUARE, -3);
		}});
		VoidMaybe voidMaybe = VoidMaybe.wrap(() -> initialTradeRequestAcceptor.acceptSupplyRequest(
				supplyTradeRequest,
				player
		)).get();
		assertThat(voidMaybe, failure(hasException(is(instanceOf(UnacceptableInputException.class)))));
		assertThat(voidMaybe, failure(hasException(hasMessage("The trade did not balance.  You can't get nothing by giving 3 squares."))));
		assertThat(player.getNumberOfType(SQUARE), is(3));
		assertThat(player.getNumberOfType(CIRCLE), is(0));
	}

	@Test
	public void should_not_accept_supply_trade_when_trader_is_giving_too_much() throws Exception {
		player.giveResources(HashBag.of(6, SQUARE));

		SupplyTradeRequest supplyTradeRequest = new SupplyTradeRequest(new HashBag<NodeType>() {{
			modifyNumberOf(SQUARE, -6);
			modifyNumberOf(CIRCLE, 1);
		}});
		VoidMaybe voidMaybe = VoidMaybe.wrap(() -> initialTradeRequestAcceptor.acceptSupplyRequest(
				supplyTradeRequest,
				player
		)).get();
		assertThat(voidMaybe, failure(hasException(is(instanceOf(UnacceptableInputException.class)))));
		assertThat(voidMaybe, failure(hasException(hasMessage("The trade did not balance.  You can't get 1 circle by giving 6 squares."))));
		assertThat(player.getNumberOfType(SQUARE), is(6));
		assertThat(player.getNumberOfType(CIRCLE), is(0));
	}

	@Test
	public void should_not_accept_supply_trade_when_trader_is_not_giving_enough() throws Exception {
		SupplyTradeRequest supplyTradeRequest = new SupplyTradeRequest(new HashBag<NodeType>() {{
			modifyNumberOf(SQUARE, 0);
			modifyNumberOf(CIRCLE, 1);
		}});
		VoidMaybe voidMaybe = VoidMaybe.wrap(() -> initialTradeRequestAcceptor.acceptSupplyRequest(
				supplyTradeRequest,
				player
		)).get();
		assertThat(voidMaybe, failure(hasException(is(instanceOf(UnacceptableInputException.class)))));
		assertThat(voidMaybe, failure(hasException(hasMessage("The trade did not balance.  You can't get 1 circle by giving nothing."))));
		assertThat(player.getNumberOfType(SQUARE), is(0));
		assertThat(player.getNumberOfType(CIRCLE), is(0));
	}

	@Test
	public void should_not_accept_supply_trade_when_not_giving_a_multiple_of_three() throws Exception {
		player.giveResources(HashBag.of(4, SQUARE));

		SupplyTradeRequest supplyTradeRequest = new SupplyTradeRequest(new HashBag<NodeType>() {{
			modifyNumberOf(SQUARE, -4);
			modifyNumberOf(CIRCLE, 1);
		}});
		VoidMaybe voidMaybe = VoidMaybe.wrap(() -> initialTradeRequestAcceptor.acceptSupplyRequest(
				supplyTradeRequest,
				player
		)).get();
		assertThat(voidMaybe, failure(hasException(hasException(is(instanceOf(UnacceptableInputException.class))))));
		assertThat(voidMaybe, failure(hasException(hasMessage("You can't give 4 squares: 4 is not a multiple of 3."))));
		assertThat(player.getNumberOfType(SQUARE), is(4));
		assertThat(player.getNumberOfType(CIRCLE), is(0));
	}
}