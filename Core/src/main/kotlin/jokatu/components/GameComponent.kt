package jokatu.components

import org.springframework.stereotype.Component

/**
 * An annotation to group a set of components that do stuff within a specific game.
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention
@Component
annotation class GameComponent(
		/**
		 * @return the name of the game for which this is a component
		 */
		val gameName: String
)
