package jokatu.identity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Steven Weston
 */
public interface IdentifiableReader<I extends Identity, J extends Identifiable<I>> {

	@Nullable
	J read(@NotNull I i);
}
