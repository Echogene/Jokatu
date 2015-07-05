package jokatu.identity;

/**
 * @author Steven Weston
 */
public interface IdentifiableReader<I extends Identity, J extends Identifiable<I>> {

	J read(I i);
}
