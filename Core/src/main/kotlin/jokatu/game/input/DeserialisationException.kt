package jokatu.game.input

import java.text.MessageFormat

/**
 * Thrown from [InputDeserialiser]s when the JSON passed to it is invalid.
 */
class DeserialisationException(json: Map<String, Any>, reason: String) : Exception(MessageFormat.format("Failed to serialise {0}\n\t{1}", json, reason))
