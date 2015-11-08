package jokatu.game.status;

import org.jetbrains.annotations.NotNull;

/**
 * The status of a game should be displayed to all observers.  One example would be ‘Waiting for players to join’.
 * @author steven
 */
public interface Status {

	/**
	 * @return the text that should be displayed to all observers and players of a game.
	 */
	@NotNull
	String getText();
}
