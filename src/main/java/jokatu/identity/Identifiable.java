package jokatu.identity;


/**
 * Some thing that is identifiable can be identified by a unique identity.
 * @author Steven Weston
 */
public interface Identifiable<I extends Identity> {

	I getIdentifier();
}
