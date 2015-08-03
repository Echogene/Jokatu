package jokatu.identity;


import org.jetbrains.annotations.NotNull;

/**
 * Some thing that is identifiable can be identified by a unique identity.
 * @author Steven Weston
 */
public interface Identifiable<I extends Identity> {

	@NotNull I getIdentifier();
}
