package jokatu.game.input

import java.text.MessageFormat

/**
 * This takes JSON from the client and turns it into input.
 * @param <I> the type of the [Input] to output
</I> */
abstract class InputDeserialiser<I : Input> {

	@Throws(DeserialisationException::class)
	abstract fun deserialise(json: Map<String, Any>): I

	@Throws(DeserialisationException::class)
	protected fun getMandatoryKeyValue(keyName: String, json: Map<String, Any>): Any {
		if (!json.containsKey(keyName)) {
			throw DeserialisationException(json, MessageFormat.format("Did not contain the key ''{0}''.", keyName))
		}
		return json[keyName]
				?: throw DeserialisationException(json, MessageFormat.format("The value for the key ''{0}'' was null.", keyName))
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
					MessageFormat.format(
							"The value for ''{0}'' was not a {1}: instead, was the {2} ''{3}''",
							keyName,
							type.simpleName,
							value.javaClass.simpleName,
							value
					)
			)
		}
		return type.cast(value)
	}
}
