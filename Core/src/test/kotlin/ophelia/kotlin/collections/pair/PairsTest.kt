package ophelia.kotlin.collections.pair

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
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
}