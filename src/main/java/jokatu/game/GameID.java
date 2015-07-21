package jokatu.game;

import jokatu.identity.Identity;

/**
 * @author Steven Weston
 */
public class GameID implements Identity {

	public final long identity;

	public GameID(long identity) {
		this.identity = identity;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		GameID gameID = (GameID) o;

		return identity == gameID.identity;
	}

	@Override
	public int hashCode() {
		return (int) (identity ^ (identity >>> 32));
	}
}
