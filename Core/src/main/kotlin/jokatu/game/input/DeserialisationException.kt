package jokatu.game.input

/**
 * Thrown from [InputDeserialiser]s when the JSON passed to it is invalid.
 */
class DeserialisationException(json: Map<String, Any>, reason: String) : Exception("Failed to serialise $json\n\t$reason}")
