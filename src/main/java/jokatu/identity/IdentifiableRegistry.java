package jokatu.identity;

import org.jetbrains.annotations.NotNull;

/**
 * A place to store {@link Identifiable} things.
 * @author Steven Weston
 */
public interface IdentifiableRegistry<I extends Identity, J extends Identifiable<I>> {

	void register(@NotNull J identifiable);
}
