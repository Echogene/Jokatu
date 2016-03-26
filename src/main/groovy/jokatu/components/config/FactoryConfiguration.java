package jokatu.components.config;

import jokatu.components.GameComponent;
import jokatu.game.Game;
import jokatu.game.GameFactory;
import jokatu.game.input.InputDeserialiser;
import jokatu.game.player.PlayerFactory;
import jokatu.game.viewresolver.ViewResolver;
import jokatu.game.viewresolver.ViewResolverFactory;
import ophelia.collections.BaseCollection;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.text.MessageFormat.format;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

/**
 * Autodetect and wire up all the relevant factories for games.
 */
@Configuration
@ComponentScan("jokatu.game")
public class FactoryConfiguration {

	private Map<String, GameConfiguration> configs;

	@Autowired
	private ApplicationContext applicationContext;

	@SuppressWarnings("unused") // This is called by Spring
	@PostConstruct
	public void populateFactories() {
		configs = getFactoryMap(GameConfiguration.class);
	}

	private <T> Map<String, T> getFactoryMap(@NotNull Class<T> clazz) {
		return applicationContext.getBeansOfType(clazz).values().stream()
				.collect(toMap(this::getGameNameFromFactoryAnnotation, identity()));
	}

	@NotNull
	private String getGameNameFromFactoryAnnotation(@NotNull Object factory) {
		GameComponent annotation = factory.getClass().getAnnotation(GameComponent.class);
		if (annotation == null) {
			throw new RuntimeException(format("{0} was not annotated with @GameComponent.", factory));
		}
		return annotation.gameName();
	}

	@Bean
	public GameFactories gameFactories() {
		return new GameFactories();
	}

	public class GameFactories {
		private final List<String> gameNames;

		private GameFactories() {
			gameNames = new ArrayList<>(configs.keySet());
			Collections.sort(gameNames);
		}

		@NotNull
		public List<String> getGameNames() {
			return gameNames;
		}

		@NotNull
		private GameConfiguration getConfig(@NotNull String gameName) {
			GameConfiguration config = configs.get(gameName);
			if (config == null) {
				throw new IllegalArgumentException(format("Unrecognised game name ''{0}''.", gameName));
			}
			return config;
		}

		@NotNull
		public GameFactory getFactory(@NotNull String gameName) {
			return getConfig(gameName).getGameFactory();
		}

		@NotNull
		public PlayerFactory getPlayerFactory(@NotNull Game<?> game) {
			String gameName = game.getGameName();
			return getConfig(gameName).getPlayerFactory();
		}

		public BaseCollection<? extends InputDeserialiser> getInputDeserialisers(@NotNull Game<?> game) {
			String gameName = game.getGameName();
			return getConfig(gameName).getInputDeserialisers();
		}

		public ViewResolver<?, ?> getViewResolver(@NotNull Game<?> game) {
			String gameName = game.getGameName();
			ViewResolverFactory<?, ?> factory = getConfig(gameName).getViewResolverFactory();
			return factory.getViewResolver(game);
		}
	}
}
