package ophelia.kotlin.collections.pair

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsNot.not
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import test.assertFailure

class PairsTest {
	@Test
	fun `should iterate over pair`() {
		val iterator = UnorderedPair(1, 2).iterator()

		assertTrue(iterator.hasNext())
		assertThat(iterator.next(), `is`(1))

		assertTrue(iterator.hasNext())
		assertThat(iterator.next(), `is`(2))

		assertFalse(iterator.hasNext())
		assertFailure { iterator.next() }
	}

	@Test
	fun `should iterate over ordered pair`() {
		val iterator = OrderedPair(1, 2).listIterator()

		assertTrue(iterator.hasNext())
		assertThat(iterator.nextIndex(), `is`(0))
		assertFalse(iterator.hasPrevious())
		assertThat(iterator.previousIndex(), `is`(-1))
		assertThat(iterator.next(), `is`(1))

		assertTrue(iterator.hasNext())
		assertThat(iterator.nextIndex(), `is`(1))
		assertTrue(iterator.hasPrevious())
		assertThat(iterator.previousIndex(), `is`(0))
		assertThat(iterator.next(), `is`(2))

		assertFalse(iterator.hasNext())
		assertThat(iterator.nextIndex(), `is`(2))
		assertTrue(iterator.hasPrevious())
		assertThat(iterator.previousIndex(), `is`(1))
		assertFailure { iterator.next() }
		assertThat(iterator.previous(), `is`(2))

		assertTrue(iterator.hasNext())
		assertThat(iterator.nextIndex(), `is`(1))
		assertTrue(iterator.hasPrevious())
		assertThat(iterator.previousIndex(), `is`(0))
		assertThat(iterator.previous(), `is`(1))

		assertTrue(iterator.hasNext())
		assertThat(iterator.nextIndex(), `is`(0))
		assertFalse(iterator.hasPrevious())
		assertThat(iterator.previousIndex(), `is`(-1))
		assertFailure { iterator.previous() }

		assertTrue(iterator.hasNext())
		assertThat(iterator.nextIndex(), `is`(0))
		assertFalse(iterator.hasPrevious())
		assertThat(iterator.previousIndex(), `is`(-1))
	}

	@Test
	fun `should iterate over singleton list`() {
		val iterator = SingletonListIterator { 1 }

		assertTrue(iterator.hasNext())
		assertThat(iterator.nextIndex(), `is`(0))
		assertFalse(iterator.hasPrevious())
		assertThat(iterator.previousIndex(), `is`(-1))
		assertThat(iterator.next(), `is`(1))

		assertFalse(iterator.hasNext())
		assertThat(iterator.nextIndex(), `is`(1))
		assertTrue(iterator.hasPrevious())
		assertThat(iterator.previousIndex(), `is`(0))
		assertFailure { iterator.next() }
		assertThat(iterator.previous(), `is`(1))

		assertTrue(iterator.hasNext())
		assertThat(iterator.nextIndex(), `is`(0))
		assertFalse(iterator.hasPrevious())
		assertThat(iterator.previousIndex(), `is`(-1))
		assertFailure { iterator.previous() }
		assertThat(iterator.next(), `is`(1))
	}

	@Test
	fun `should consider unordered pairs the same`() {
		assertThat(UnorderedPair(1, 2), `is`(UnorderedPair(2, 1)))
		assertThat(UnorderedPair(1, 2).hashCode(), `is`(UnorderedPair(2, 1).hashCode()))
	}

	@Test
	fun `should consider ordered pairs not the same`() {
		assertThat(OrderedPair(1, 2), `is`(not(OrderedPair(2, 1))))
		assertThat(OrderedPair(1, 2).hashCode(), `is`(not(OrderedPair(2, 1).hashCode())))
	}
}