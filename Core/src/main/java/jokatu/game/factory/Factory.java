package jokatu.game.factory;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation to group a set of components that create stuff within a specific game.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface Factory {
	/**
	 * @return the name of the game for which this is a factory
	 */
	@NotNull String gameName();
}
