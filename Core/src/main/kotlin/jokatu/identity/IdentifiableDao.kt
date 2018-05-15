package jokatu.identity

/**
 * A place to store and retrieve [Identifiable] objects.
 */
interface IdentifiableDao<I : Identity, J : Identifiable<I>> : IdentifiableReader<I, J>, IdentifiableRegistry<I, J>
