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
import java.util.*;

import static java.text.MessageFormat.format;

@Configuration
@ComponentScan("jokatu.game")
public class FactoryConfiguration {

	private final Map<String, GameFactory> gameFactories = new HashMap<>();

	@Autowired
	private ApplicationContext applicationContext;

	@PostConstruct
	public void populateFactories() {
		Map<String, GameFactory> factoryBeans = applicationContext.getBeansOfType(GameFactory.class);
		for (GameFactory factory : factoryBeans.values()) {
			gameFactories.put(getGameNameFromFactoryAnnotation(factory), factory);
		}
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
		return new GameFactories(gameFactories);
	}

	public static class GameFactories {
		private final Map<String, GameFactory> gameFactories;
		private final List<String> gameNames;

		private GameFactories(Map<String, GameFactory> gameFactories) {
			this.gameFactories = gameFactories;
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
