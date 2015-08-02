package jokatu.components.config;

import jokatu.game.factory.GameFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.*;

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
			gameFactories.put(factory.getGameName(), factory);
		}
	}

	@Bean
	public GameFactories gameFactories() {
		return new GameFactories(gameFactories);
	}

	public static class GameFactories {
		private final Map<String, GameFactory> gameFactories;

		private GameFactories(Map<String, GameFactory> gameFactories) {
			this.gameFactories = gameFactories;
		}

		public List<String> getGameNames() {
			ArrayList<String> gameNames = new ArrayList<>(gameFactories.keySet());
			Collections.sort(gameNames);
			return gameNames;
		}

		public GameFactory getFactory(String gameNames) {
			return gameFactories.get(gameNames);
		}
	}
}
