package jokatu.components.config

import jokatu.game.input.Input
import jokatu.game.input.InputDeserialiser
import ophelia.exceptions.voidmaybe.VoidMaybe.wrap
import ophelia.exceptions.voidmaybe.VoidMaybeCollectors.merge
import ophelia.function.ExceptionalConsumer
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import java.lang.reflect.Modifier
import java.text.MessageFormat
import java.util.*
import java.util.stream.Collectors
import javax.annotation.PostConstruct

@Configuration
@ComponentScan("jokatu.game")
class InputDeserialisers
@Autowired constructor(
		private val applicationContext: ApplicationContext
) {
	private val deserialiserMap = HashMap<Class<out Input>, InputDeserialiser<*>>()

	@PostConstruct
	@Throws(Exception::class)
	fun populateDeserialisers() {
		applicationContext.getBeansOfType(InputDeserialiser::class.java).values.stream()
				.map(wrap(ExceptionalConsumer<InputDeserialiser<*>, Exception> { this.addDeserialiser(it) }))
				.collect(merge())
				.throwOnFailure()
		if (deserialiserMap.isEmpty()) {
			throw Exception("No input deserialisers could be found.")
		}

		log.debug("{} initialised", InputDeserialisers::class.java.simpleName)
	}

	fun getDeserialiser(inputClass: Class<out Input>): InputDeserialiser<*>? {
		return deserialiserMap[inputClass]
	}

	@Throws(Exception::class)
	private fun addDeserialiser(inputDeserialiser: InputDeserialiser<*>) {
		val inputType = getInputType(inputDeserialiser)
		if (isAbstract(inputType)) {
			throw Exception(MessageFormat.format(
					"The deserialiser {0} returns the abstract input class {1}",
					inputDeserialiser.javaClass.simpleName,
					inputType.simpleName
			))
		}
		if (deserialiserMap.containsKey(inputType)) {
			throw Exception(MessageFormat.format("The input class {0} has multiple deserialisers", inputType.simpleName))
		}
		deserialiserMap[inputType] = inputDeserialiser
	}

	private fun isAbstract(inputType: Class<*>): Boolean {
		return inputType.modifiers and Modifier.ABSTRACT == Modifier.ABSTRACT
	}

	@Throws(Exception::class)
	private fun getInputType(deserialiser: InputDeserialiser<*>): Class<out Input> {
		val deserialiseReturnClasses = Arrays.stream(deserialiser.javaClass.declaredMethods)
				.filter { method -> Input::class.java != method.returnType }
				.filter { method -> Input::class.java.isAssignableFrom(method.returnType) }
				.map { it.returnType }
				.collect(Collectors.toSet())
		when {
			deserialiseReturnClasses.size == 1 -> return deserialiseReturnClasses.iterator().next() as Class<out Input>
			deserialiseReturnClasses.size > 1 -> throw Exception(MessageFormat.format(
					"The deserialiser {0} has multiple declared methods that have a return type of a strict subclass of {1}.",
					deserialiser.javaClass.simpleName,
					Input::class.java.simpleName
			))
			else -> throw Exception(MessageFormat.format(
					"The deserialiser {0} has no declared methods that have a return type of a strict subclass of {1}.",
					deserialiser.javaClass.simpleName,
					Input::class.java.simpleName
			))
		}
	}

	companion object {
		private val log = LoggerFactory.getLogger(InputDeserialisers::class.java)
	}
}
