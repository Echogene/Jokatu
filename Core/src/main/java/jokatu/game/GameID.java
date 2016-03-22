package jokatu.game;

import com.fasterxml.jackson.annotation.JsonValue;
import jokatu.identity.Identity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An object that uniquely identifies a game.
 * @author Steven Weston
 */
public class GameID implements Identity, Comparable<GameID> {

	private long identity;

	@SuppressWarnings("unused") // This is called by Jackson
	public GameID() {}

	public GameID(long identity) {setIdentity(identity);}

	@SuppressWarnings("unused") // This is called by Jackson
	@JsonValue
	public long getIdentity() {
		return identity;
	}

	public void setIdentity(long identity) {
		this.identity = identity;
	}

	@Override
	public boolean equals(@Nullable Object o) {

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

	@Override
	public String toString() {
		return Long.toString(identity);
	}

	@Override
	public int compareTo(@NotNull GameID o) {
		if (identity < o.identity) {
			return -1;
		} else if (identity > o.identity) {
			return 1;
		} else {
			return 0;
		}
	}
}
