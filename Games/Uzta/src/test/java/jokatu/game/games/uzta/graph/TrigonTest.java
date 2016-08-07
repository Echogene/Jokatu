package jokatu.game.games.uzta.graph;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TrigonTest {

	@Test
	public void circumcircle_should_be_correct() throws Exception {
		Trigon trigon = new Trigon(
				new Node("0", 0, 0),
				new Node("x", 1, 0),
				new Node("y", 0, 1)
		);
		assertThat(trigon.circumcircleContains(node( 0.01,     0)), is(true));
		assertThat(trigon.circumcircleContains(node(    0,  0.01)), is(true));
		assertThat(trigon.circumcircleContains(node(-0.01,     0)), is(false));
		assertThat(trigon.circumcircleContains(node(    0, -0.01)), is(false));

		assertThat(trigon.circumcircleContains(node(0.99,     0)), is(true));
		assertThat(trigon.circumcircleContains(node(   1,  0.01)), is(true));
		assertThat(trigon.circumcircleContains(node(1.01,     0)), is(false));
		assertThat(trigon.circumcircleContains(node(   1, -0.01)), is(false));

		assertThat(trigon.circumcircleContains(node(    0, 0.99)), is(true));
		assertThat(trigon.circumcircleContains(node( 0.01,    1)), is(true));
		assertThat(trigon.circumcircleContains(node(    0, 1.01)), is(false));
		assertThat(trigon.circumcircleContains(node(-0.01,    1)), is(false));
	}

	@NotNull
	private Node node(double x, double y) {
		return new Node("lol", x, y);
	}
}