package ophelia.kotlin.graph

import ophelia.kotlin.collections.pair.Pair
import ophelia.kotlin.collections.pair.UnorderedPair

interface Graph<out N: Any, out E: Collection<N>> {
	val nodes: Collection<N>
	val edges: Collection<E>
}

interface BiGraph<out N: Any, out E: Pair<N>>: Graph<N, E>
interface UndirectedGraph<out N: Any, out E: UnorderedPair<N>>: BiGraph<N, E>

interface MutableGraph<N: Any, E: Collection<N>>: Graph<N, E> {
	override val nodes: MutableCollection<N>
	override val edges: MutableCollection<E>
}

interface MutableBiGraph<N: Any, E: Pair<N>>: MutableGraph<N, E>
interface MutableUndirectedGraph<N: Any, E: UnorderedPair<N>>: MutableBiGraph<N, E>