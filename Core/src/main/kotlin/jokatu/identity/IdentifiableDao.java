package jokatu.identity;

/**
 * A place to store and retrieve {@link Identifiable} objects.
 */
public interface IdentifiableDao<I extends Identity, J extends Identifiable<I>>
		extends IdentifiableReader<I, J>,
				IdentifiableRegistry<I, J> {}
