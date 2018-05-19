package jokatu.identity

/**
 * An object that returns an [Identifiable] with the given [Identity].
 * @author Steven Weston
 */
interface IdentifiableReader<I : Identity, J : Identifiable<I>> {

	/**
	 * @return the [Identifiable] with the given [Identity] or `null` if no such `Identifiable` exists
	 */
	fun read(identity: I): J?

	/**
	 * @return a count of all the entities that can be read
	 */
	fun count(): Long
}
