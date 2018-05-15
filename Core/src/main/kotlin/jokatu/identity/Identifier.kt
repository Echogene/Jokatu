package jokatu.identity

import java.util.function.Supplier

/**.
 * An identifier provides identities.  The identities should be unique every time [get()][Supplier.get] is
 * called.
 * @author Steven Weston
 */
interface Identifier<I : Identity> : Supplier<I>
