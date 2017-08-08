package jokatu.game.player;

import org.jetbrains.annotations.NotNull;

/**
 * An abstract player has a name.
 */
public class AbstractPlayer implements Player {
	private final String name;

	protected AbstractPlayer(String username) {
		this.name = username;
	}

	@NotNull
	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		AbstractPlayer that = (AbstractPlayer) o;

		return name.equals(that.name);

	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public String toString() {
		return getName();
	}
}
