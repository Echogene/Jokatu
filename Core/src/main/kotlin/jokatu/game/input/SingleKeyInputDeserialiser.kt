package jokatu.game.input

/**
 * An [InputDeserialiser] where the JSON is expected to have only one key.
 * @param <I> the type of the [Input] to output
 */
abstract class SingleKeyInputDeserialiser<I : Input>(protected val keyName: String) : InputDeserialiser<I>() {
	@Throws(DeserialisationException::class)
	override fun deserialise(json: Map<String, Any>): I {
		if (json.size > 1) {
			throw DeserialisationException(json, "There was more than one key.")
		}
		val value = getMandatoryKeyValue(keyName, json)
		return deserialiseSingleValue(json, value)
	}

	@Throws(DeserialisationException::class)
	protected abstract fun deserialiseSingleValue(json: Map<String, Any>, value: Any): I
}
