package jokatu.game.input

import org.springframework.util.ClassUtils

/**
 * A [SingleKeyInputDeserialiser] where we care about the type of the value for the single key.
 * @param <T> the expected type of the single key's value
 * @param <I> the type of the [Input] to output
 */
abstract class TypedSingleKeyInputDeserialiser<T, I : Input>(
		rawType: Class<T>
) : SingleKeyInputDeserialiser<I>() {

	@Suppress("UNCHECKED_CAST")
	protected val wrappedType: Class<T> = ClassUtils.resolvePrimitiveIfNecessary(rawType) as Class<T>

	@Throws(DeserialisationException::class)
	override fun deserialiseSingleValue(json: Map<String, Any>, value: Any): I {
		val castValue = castValue(wrappedType, keyName, value, json)
		return deserialiseTypedSingleValue(json, castValue)
	}

	@Throws(DeserialisationException::class)
	protected abstract fun deserialiseTypedSingleValue(json: Map<String, Any>, value: T): I
}
