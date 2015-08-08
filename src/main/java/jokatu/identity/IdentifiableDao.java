package jokatu.identity;

public interface IdentifiableDao<I extends Identity, J extends Identifiable<I>>
		extends IdentifiableReader<I, J>,
				IdentifiableRegistry<I, J> {}
