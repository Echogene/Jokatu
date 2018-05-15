package jokatu.identity

/**
 * A place to store [Identifiable] things.
 * @author Steven Weston
 */
interface IdentifiableRegistry<I : Identity, J : Identifiable<I>> {

	fun register(identifiable: J)
}
