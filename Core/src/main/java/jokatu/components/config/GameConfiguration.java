package jokatu.components.config;

import jokatu.game.GameFactory;
import jokatu.game.input.InputDeserialiser;
import jokatu.game.player.AbstractPlayerFactory;
import jokatu.game.player.PlayerFactory;
import jokatu.game.viewresolver.ViewResolverFactory;
import ophelia.collections.BaseCollection;
import org.jetbrains.annotations.NotNull;

/**
 * Groups together all the necessary components for a type of game.
 * @author steven
 */
public interface GameConfiguration {

	/**
	 * @return the {@link GameFactory} that will be used to construct games of this type
	 */
	@NotNull
	GameFactory<?> getGameFactory();

	/**
	 * @return the {@link AbstractPlayerFactory} that will be used to create players for games of this type
	 */
	@NotNull
	PlayerFactory<?> getPlayerFactory();

	/**
	 * @return the {@link InputDeserialiser}s that will be used to deserialise client inputs for games of this type
	 */
	@NotNull
	BaseCollection<? extends InputDeserialiser> getInputDeserialisers();

	/**
	 * @return the {@link ViewResolverFactory} that will be used to determine the views that the client should use for
	 *         games of this type
	 */
	@NotNull
	ViewResolverFactory<?, ?> getViewResolverFactory();
}
