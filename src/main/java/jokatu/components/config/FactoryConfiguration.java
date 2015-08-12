package jokatu.components.config;

import jokatu.game.Game;
import jokatu.game.factory.Factory;
import jokatu.game.factory.game.GameFactory;
import jokatu.game.factory.input.InputDeserialiser;
import jokatu.game.factory.player.PlayerFactory;
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

@Configuration
@ComponentScan("jokatu.game")
public class FactoryConfiguration {

	private Map<String, GameFactory> gameFactories;
	private Map<String, PlayerFactory> playerFactories;
	private Map<String, InputDeserialiser> inputDeserialisers;

	@Autowired
	private ApplicationContext applicationContext;

	@PostConstruct
	public void populateFactories() {
		gameFactories = getFactoryMap(GameFactory.class);
		playerFactories = getFactoryMap(PlayerFactory.class);
		inputDeserialisers = getFactoryMap(InputDeserialiser.class);
	}

	private <T> Map<String, T> getFactoryMap(@NotNull Class<T> clazz) {
		return applicationContext.getBeansOfType(clazz).values().stream()
				.collect(toMap(this::getGameNameFromFactoryAnnotation, identity()));
	}

	@NotNull
	private String getGameNameFromFactoryAnnotation(@NotNull Object factory) {
		Factory annotation = factory.getClass().getAnnotation(Factory.class);
		if (annotation == null) {
			throw new RuntimeException(format("{0} was not annotated with @Factory.", factory));
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
			gameNames = new ArrayList<>(gameFactories.keySet());
			Collections.sort(gameNames);
		}

		@NotNull
		public List<String> getGameNames() {
			return gameNames;
		}

		@NotNull
		public <G extends Game<?, ?, ?, ?>> GameFactory<G> getFactory(@NotNull String gameName) {
			GameFactory factory = gameFactories.get(gameName);
			if (factory == null) {
				throw new NullPointerException(format("The game ''{0}'' has no factory.", gameName));
			}
			return factory;
		}

		@NotNull
		public PlayerFactory getPlayerFactory(@NotNull Game game) {
			String gameName = game.getGameName();
			PlayerFactory factory = playerFactories.get(gameName);
			if (factory == null) {
				throw new NullPointerException(format("The game ''{0}'' has no player factory.", gameName));
			}
			return factory;
		}

		public InputDeserialiser getInputDeserialiser(@NotNull Game game) {
			String gameName = game.getGameName();
			InputDeserialiser factory = inputDeserialisers.get(gameName);
			if (factory == null) {
				throw new NullPointerException(format("The game ''{0}'' has no input deserialiser.", gameName));
			}
			return factory;
		}
	}
}
