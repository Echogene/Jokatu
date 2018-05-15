package jokatu.components.config

import jokatu.components.GameComponent
import jokatu.game.Game
import jokatu.game.GameFactory
import jokatu.game.player.PlayerFactory
import jokatu.game.viewresolver.ViewResolver
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import java.text.MessageFormat.format
import java.util.*
import java.util.stream.Collectors.toMap
import javax.annotation.PostConstruct

/**
 * Autodetect and wire up all the relevant factories for games.
 */
@Configuration
@ComponentScan("jokatu.game")
class FactoryConfiguration {

	private lateinit var configs: Map<String, GameConfiguration>

	@Autowired
	private lateinit var applicationContext: ApplicationContext

	// This is called by Spring
	@PostConstruct
	fun populateFactories() {
		configs = getFactoryMap(GameConfiguration::class.java)

		log.debug("{} initialised", FactoryConfiguration::class.java.simpleName)
	}

	private fun <T: Any> getFactoryMap(clazz: Class<T>): Map<String, T> {
		return applicationContext.getBeansOfType(clazz).values.stream()
				.collect(toMap(::getGameNameFromFactoryAnnotation, {t: T -> t}))
	}

	private fun <T: Any> getGameNameFromFactoryAnnotation(factory: T): String {
		val annotation = factory.javaClass.getAnnotation(GameComponent::class.java)
				?: throw RuntimeException(format("{0} was not annotated with @GameComponent.", factory))
		return annotation.gameName
	}

	@Bean
	fun gameFactories(): GameFactories {
		return GameFactories()
	}

	inner class GameFactories {
		val gameNames: List<String>

		init {
			gameNames = ArrayList(configs.keys)
			Collections.sort(gameNames)
		}

		private fun getConfig(gameName: String): GameConfiguration {
			return configs[gameName]
					?: throw IllegalArgumentException(format("Unrecognised game name ''{0}''.", gameName))
		}

		fun getFactory(gameName: String): GameFactory<*> {
			return getConfig(gameName).gameFactory
		}

		fun getPlayerFactory(game: Game<*>): PlayerFactory<*> {
			val gameName = game.gameName
			return getConfig(gameName).playerFactory
		}

		fun getViewResolver(game: Game<*>): ViewResolver<*, *> {
			val gameName = game.gameName
			val factory = getConfig(gameName).viewResolverFactory
			return factory.getViewResolver(game)
		}
	}

	companion object {

		private val log = LoggerFactory.getLogger(FactoryConfiguration::class.java)
	}
}
