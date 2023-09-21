package ophelia.kotlin.collections.pair

interface Pair<out E: Any>: Collection<E>

abstract class AbstractPair<out E: Any>(val first: E, val second: E): Pair<E> {
	init {
		if (first === second) {
			throw IllegalArgumentException("The two entries in a pair cannot be the same.")
		}
	}
	override val size: Int
		get() = 2

	override fun isEmpty() = false

	override fun contains(element: @UnsafeVariance E) = first == element || second == element

	override fun containsAll(elements: Collection<@UnsafeVariance E>) = elements.all(::contains)

	override fun iterator() = PairIterator()

	open inner class PairIterator: Iterator<E> {
		protected var index = 0
		override fun hasNext() = index < 2

		override fun next(): E {
			val element = when (index) {
				0 -> first
				1 -> second
				else -> throw NoSuchElementException()
			}
			index++
			return element
		}
	}

	fun other(element: @UnsafeVariance E) = when (element) {
		first -> second
		second -> first
		else -> null
	}
}

open class UnorderedPair<out E: Any>(first: E, second: E): AbstractPair<E>(first, second), Set<E> {
	override fun toString(): String = "{$first, $second}"

	override fun equals(other: Any?) = when (other) {
		is UnorderedPair<*> -> (first == other.first && second == other.second)
							|| (first == other.second && second == other.first)
		else -> false
	}

	override fun hashCode(): Int {
		return first.hashCode() xor second.hashCode()
	}
}

class OrderedPair<out E: Any>(first: E, second: E): AbstractPair<E>(first, second), List<E> {
	override fun get(index: Int) = when (index) {
		0 -> first
		1 -> second
		else -> throw IndexOutOfBoundsException("$index")
	}

	override fun lastIndexOf(element: @UnsafeVariance E) = indexOf(element)
	override fun indexOf(element: @UnsafeVariance E) = when (element) {
		first -> 0
		second -> 1
		else -> -1
	}

	override fun listIterator(): ListIterator<E> = PairListIterator()
	override fun listIterator(index: Int) = when (index) {
		in 0..1 -> PairListIterator(index)
		else -> throw IndexOutOfBoundsException("$index")
	}

	override fun subList(fromIndex: Int, toIndex: Int) = when {
		fromIndex < 0 || fromIndex > 1 -> throw IndexOutOfBoundsException("fromIndex = $fromIndex")
		toIndex > 2 -> throw IndexOutOfBoundsException("toIndex = $toIndex")
		fromIndex > toIndex -> throw IllegalArgumentException("$fromIndex > $toIndex")
		fromIndex == toIndex -> emptyList()
		fromIndex == 0 && toIndex == 2 -> this
		fromIndex == 0 -> SingletonListView { this[0] }
		else -> SingletonListView { this[1] }
	}

	inner class PairListIterator(start: Int = 0): PairIterator(), ListIterator<E> {
		init {
			index = start
		}

		override fun hasPrevious() = index > 0
		override fun nextIndex() = index
		override fun previousIndex() = index - 1

		override fun previous(): E {
			val element = when (index) {
				1 -> first
				2 -> second
				else -> throw NoSuchElementException()
			}
			index--
			return element
		}
	}

	override fun toString(): String = "[$first, $second]"

	override fun equals(other: Any?) = when (other) {
		is OrderedPair<*> -> (first == other.first && second == other.second)
		else -> false
	}

	override fun hashCode(): Int {
		var result = first.hashCode()
		result = 31 * result + second.hashCode()
		return result
	}
}

/**
 * A singleton list that wraps a supplier.
 */
class SingletonListView<out E>(private val elementSupplier: () -> E): List<E> {
	override val size: Int
		get() = 1

	override fun isEmpty() = false

	override fun contains(element: @UnsafeVariance E) = elementSupplier() == element
	override fun containsAll(elements: Collection<@UnsafeVariance E>) = elements.all(::contains)

	override fun get(index: Int) = when (index) {
		0 -> elementSupplier()
		else -> throw IndexOutOfBoundsException("$index")
	}

	override fun lastIndexOf(element: @UnsafeVariance E) = indexOf(element)
	override fun indexOf(element: @UnsafeVariance E) = when (element) {
		elementSupplier() -> 0
		else -> -1
	}

	override fun iterator(): Iterator<E> = listIterator()
	override fun listIterator(): ListIterator<E> = SingletonListIterator(elementSupplier)
	override fun listIterator(index: Int): ListIterator<E> = when (index) {
		0 -> listIterator()
		else -> throw IndexOutOfBoundsException("$index")
	}

	override fun subList(fromIndex: Int, toIndex: Int) = when {
		fromIndex != 0 -> throw IndexOutOfBoundsException("fromIndex = $fromIndex")
		toIndex < 0 || toIndex > 1 -> throw IndexOutOfBoundsException("toIndex = $toIndex")
		toIndex == 0 -> emptyList<E>()
		else -> this
	}
}

class SingletonListIterator<out E>(private val elementSupplier: () -> E): ListIterator<E> {
	private var readFirst = false

	override fun hasNext() = !readFirst
	override fun hasPrevious() = readFirst
	override fun nextIndex() = if (readFirst) 1 else 0
	override fun previousIndex() = if (readFirst) 0 else -1

	override fun next() = when {
		readFirst -> throw NoSuchElementException()
		else -> {
			readFirst = true
			elementSupplier()
		}
	}

	override fun previous() = when {
		!readFirst -> throw NoSuchElementException()
		else -> {
			readFirst = false
			elementSupplier()
		}
	}
}