package jokatu.game.input

/**
 * This takes JSON from the client and turns it into input.
 * @param <I> the type of the [Input] to output
 */
abstract class InputDeserialiser<I : Input> {

	@Throws(DeserialisationException::class)
	abstract fun deserialise(json: Map<String, Any>): I

	@Throws(DeserialisationException::class)
	protected fun getMandatoryKeyValue(keyName: String, json: Map<String, Any>): Any {
		if (!json.containsKey(keyName)) {
			throw DeserialisationException(json, "Did not contain the key '$keyName'.")
		}
		return json[keyName]
				?: throw DeserialisationException(json, "The value for the key '$keyName' was null.")
	}

	@Throws(DeserialisationException::class)
	protected fun <T> getMandatoryKeyValueOfType(
			type: Class<T>,
			keyName: String,
			json: Map<String, Any>
	): T {
		return castValue(type, keyName, getMandatoryKeyValue(keyName, json), json)
	}

	@Throws(DeserialisationException::class)
	protected fun <T> castValue(
			type: Class<T>,
			keyName: String,
			value: Any,
			json: Map<String, Any>
	): T {
		if (!type.isInstance(value)) {
			throw DeserialisationException(
					json,
					"The value for '$keyName' was not a ${type.simpleName}: instead, was the ${value.javaClass.simpleName} '$value'."
			)
		}
		return type.cast(value)
	}
}
