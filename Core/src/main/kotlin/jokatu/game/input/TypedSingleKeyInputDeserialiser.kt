package jokatu.game.input

import kotlin.reflect.KClass

/**
 * A [SingleKeyInputDeserialiser] where we care about the type of the value for the single key.
 * @param <T> the expected type of the single key's value
 * @param <I> the type of the [Input] to output
 */
abstract class TypedSingleKeyInputDeserialiser<T : Any, I : Input>(
		private val type: KClass<T>
) : SingleKeyInputDeserialiser<I>() {

	@Throws(DeserialisationException::class)
	override fun deserialiseSingleValue(json: Map<String, Any>, value: Any): I {
		val castValue = castValue(type, keyName, value, json)
		return deserialiseTypedSingleValue(json, castValue)
	}

	@Throws(DeserialisationException::class)
	protected abstract fun deserialiseTypedSingleValue(json: Map<String, Any>, value: T): I
}
