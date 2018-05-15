package jokatu.identity

/**
 * Some thing that is identifiable can be identified by a unique identity.
 * @author Steven Weston
 */
interface Identifiable<I : Identity> {

	/**
	 * @return the unique thing that identifies this object
	 */
	val identifier: I
}
