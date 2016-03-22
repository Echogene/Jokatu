package jokatu.components.config;

import jokatu.game.factory.game.GameFactory;
import jokatu.game.factory.input.InputDeserialiser;
import jokatu.game.factory.player.PlayerFactory;
import jokatu.game.viewresolver.ViewResolverFactory;
import ophelia.collections.BaseCollection;
import org.jetbrains.annotations.NotNull;

/**
 * @author steven
 */
public interface GameConfiguration {

	@NotNull
	GameFactory getGameFactory();

	@NotNull
	PlayerFactory getPlayerFactory();

	@NotNull
	BaseCollection<? extends InputDeserialiser> getInputDeserialisers();

	@NotNull
	ViewResolverFactory getViewResolverFactory();
}
