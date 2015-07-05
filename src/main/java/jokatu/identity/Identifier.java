package jokatu.identity;

import java.util.function.Supplier;

/**.
 * An identifier provides identities.  The identities should be unique every time {@link Supplier#get() get()} is
 * called.
 * @author Steven Weston
 */
public interface Identifier<I extends Identity> extends Supplier<I> {

}
