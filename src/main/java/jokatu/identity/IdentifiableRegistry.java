package jokatu.identity;

/**
 * @author Steven Weston
 */
public interface IdentifiableRegistry<I extends Identity, J extends Identifiable<I>> {

	void register(J identifiable);
}
