package jokatu.components.config;

import jokatu.game.Game;
import jokatu.game.factory.Factory;
import jokatu.game.factory.game.GameFactory;
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

	@Autowired
	private ApplicationContext applicationContext;

	@PostConstruct
	public void populateFactories() {
		gameFactories = getFactoryMap(GameFactory.class);
	}

	private <T> Map<String, T> getFactoryMap(Class<T> clazz) {
		return applicationContext.getBeansOfType(clazz).values().stream()
				.collect(toMap(this::getGameNameFromFactoryAnnotation, identity()));
	}

	@NotNull
	private String getGameNameFromFactoryAnnotation(Object factory) {
		Factory annotation = factory.getClass().getAnnotation(Factory.class);
		if (annotation == null) {
			throw new RuntimeException(format("{0} was not annotated with @Factory.", factory));
		}
		return annotation.value();
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

		public List<String> getGameNames() {
			return gameNames;
		}

		public GameFactory getFactory(String gameName) {
			return gameFactories.get(gameName);
		}

		public GameFactory getFactory(Game game) {
			return getFactory(game.getGameName());
		}
	}
}
