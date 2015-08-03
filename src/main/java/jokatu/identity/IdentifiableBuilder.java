package jokatu.identity;

import org.jetbrains.annotations.NotNull;

/**
 * @author Steven Weston
 */
public interface IdentifiableBuilder<I extends Identity, J extends Identifiable<I>> {

	J build(@NotNull I identity);
}
