package jokatu.components.config;

import jokatu.game.input.Input;
import jokatu.game.input.InputDeserialiser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static ophelia.exceptions.voidmaybe.VoidMaybe.wrap;
import static ophelia.exceptions.voidmaybe.VoidMaybeCollectors.merge;

@Configuration
@ComponentScan("jokatu.game")
public class InputDeserialisers {

	private static final Logger log = LoggerFactory.getLogger(InputDeserialisers.class);

	private Map<Class<? extends Input>, InputDeserialiser<?>> deserialiserMap = new HashMap<>();

	private final ApplicationContext applicationContext;

	@Autowired
	public InputDeserialisers(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@PostConstruct
	public void populateDeserialisers() throws Exception {
		applicationContext.getBeansOfType(InputDeserialiser.class).values().stream()
				.map(wrap(this::addDeserialiser))
				.collect(merge())
				.throwOnFailure();
		if (deserialiserMap.isEmpty()) {
			throw new Exception("No input deserialisers could be found.");
		}

		log.debug("{} initialised", InputDeserialisers.class.getSimpleName());
	}

	@Nullable
	public InputDeserialiser<?> getDeserialiser(@NotNull Class<? extends Input> inputClass) {
		return deserialiserMap.get(inputClass);
	}

	private void addDeserialiser(@NotNull InputDeserialiser inputDeserialiser) throws Exception {
		Class<? extends Input> inputType = getInputType(inputDeserialiser);
		if (isAbstract(inputType)) {
			throw new Exception(MessageFormat.format(
					"The deserialiser {0} returns the abstract input class {1}",
					inputDeserialiser.getClass().getSimpleName(),
					inputType.getSimpleName()
			));
		}
		if (deserialiserMap.containsKey(inputType)) {
			throw new Exception(MessageFormat.format("The input class {0} has multiple deserialisers", inputType.getSimpleName()));
		}
		deserialiserMap.put(inputType, inputDeserialiser);
	}

	private boolean isAbstract(@NotNull Class<?> inputType) {
		return (inputType.getModifiers() & Modifier.ABSTRACT) == Modifier.ABSTRACT;
	}

	@NotNull
	private Class<? extends Input> getInputType(@NotNull InputDeserialiser deserialiser) throws Exception {
		Set<Class<?>> deserialiseReturnClasses = Arrays.stream(deserialiser.getClass().getDeclaredMethods())
				.filter(method -> !Input.class.equals(method.getReturnType()))
				.filter(method -> Input.class.isAssignableFrom(method.getReturnType()))
				.map(Method::getReturnType)
				.collect(Collectors.toSet());
		if (deserialiseReturnClasses.size() == 1) {
			//noinspection unchecked
			return (Class<? extends Input>) deserialiseReturnClasses.iterator().next();

		} else if (deserialiseReturnClasses.size() > 1) {
			throw new Exception(MessageFormat.format(
					"The deserialiser {0} has multiple declared methods that have a return type of a strict subclass of {1}.",
					deserialiser.getClass().getSimpleName(),
					Input.class.getSimpleName()
			));
		} else {
			throw new Exception(MessageFormat.format(
					"The deserialiser {0} has no declared methods that have a return type of a strict subclass of {1}.",
					deserialiser.getClass().getSimpleName(),
					Input.class.getSimpleName()
			));
		}
	}
}
