package jokatu.identity;

/**
 * @author Steven Weston
 */
public interface IdentifiableBuilder<I extends Identity, J extends Identifiable<I>> {

	J build(I identity);
}
