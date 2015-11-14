package jokatu.identity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An object that returns an {@link Identifiable} with the given {@link Identity}.
 * @author Steven Weston
 */
public interface IdentifiableReader<I extends Identity, J extends Identifiable<I>> {

	@Nullable
	J read(@NotNull I identity);
}
