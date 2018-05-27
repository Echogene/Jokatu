package jokatu.components.config

import jokatu.game.input.Input
import jokatu.game.input.InputDeserialiser
import ophelia.exceptions.voidmaybe.VoidMaybe.wrap
import ophelia.exceptions.voidmaybe.VoidMaybeCollectors.merge
import ophelia.function.ExceptionalConsumer
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.getBeansOfType
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import reflect.asSubclass
import java.util.*
import java.util.stream.Collectors
import javax.annotation.PostConstruct
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.full.isSubclassOf

@Configuration
@ComponentScan("jokatu.game")
class InputDeserialisers
@Autowired constructor(
		private val applicationContext: ApplicationContext
) {
	private val deserialiserMap = HashMap<KClass<out Input>, InputDeserialiser<*>>()

	@PostConstruct
	@Throws(Exception::class)
	fun populateDeserialisers() {
		applicationContext.getBeansOfType(InputDeserialiser::class).stream()
				.map(wrap(ExceptionalConsumer<InputDeserialiser<*>, Exception> { this.addDeserialiser(it) }))
				.collect(merge())
				.throwOnFailure()
		if (deserialiserMap.isEmpty()) {
			throw Exception("No input deserialisers could be found.")
		}

		log.debug("{} initialised", InputDeserialisers::class.java.simpleName)
	}

	fun getDeserialiser(inputClass: KClass<out Input>): InputDeserialiser<*>? {
		return deserialiserMap[inputClass]
	}

	@Throws(Exception::class)
	private fun addDeserialiser(inputDeserialiser: InputDeserialiser<*>) {
		val inputType = getInputType(inputDeserialiser)
		if (inputType.isAbstract) {
			throw Exception(
					"The deserialiser ${inputDeserialiser.javaClass.simpleName} returns the abstract input class "
							+ "${inputType.simpleName}."
			)
		}
		if (deserialiserMap.containsKey(inputType)) {
			throw Exception("The input class ${inputType.simpleName} has multiple deserialisers.")
		}
		deserialiserMap[inputType] = inputDeserialiser
	}

	@Throws(Exception::class)
	private fun getInputType(deserialiser: InputDeserialiser<*>): KClass<out Input> {
		val deserialiseReturnClasses = deserialiser::class.declaredMemberFunctions.stream()
				.map { method -> method.returnType.classifier }
				.filter { clazz -> clazz is KClass<*> }
				.map { clazz -> clazz as KClass<*> }
				.filter { clazz -> clazz.isSubclassOf(Input::class) && clazz != Input::class }
				.map { clazz -> clazz.asSubclass(Input::class) }
				.collect(Collectors.toSet())
		when {
			deserialiseReturnClasses.size == 1 -> return deserialiseReturnClasses.first()
			deserialiseReturnClasses.size > 1 -> throw Exception(
					"The deserialiser ${deserialiser::class.simpleName} has multiple declared methods that have a "
							+ "return type of a strict subclass of ${Input::class.simpleName}."
			)
			else -> throw Exception(
					"The deserialiser ${deserialiser::class.simpleName} has no declared methods that have a return "
							+ "type of a strict subclass of ${Input::class.simpleName}."
			)
		}
	}

	companion object {
		private val log = LoggerFactory.getLogger(InputDeserialisers::class.java)
	}
}
