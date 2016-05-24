package jokatu.game.games.cards.input;

import jokatu.game.input.DeserialisationException;
import jokatu.game.input.InputDeserialiser;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Map;

import static jokatu.game.cards.Cards.ALL_CARDS;

@Component
public class CardInputDeserialiser implements InputDeserialiser<CardInput> {

	@NotNull
	@Override
	public CardInput deserialise(@NotNull Map<String, Object> json) throws DeserialisationException {
		if (!json.containsKey("card")) {
			throw new DeserialisationException(json, "Did not contain the key 'card'.");
		}
		Object value = json.get("card");
		if (!(value instanceof String)) {
			throw new DeserialisationException(json, "The value for 'card' was not a string.");
		}
		String cardDisplay = (String) value;
		if (!ALL_CARDS.containsKey(cardDisplay)) {
			throw new DeserialisationException(json, "The value for 'card' was not a valid card.");
		}
		return new CardInput(ALL_CARDS.get(cardDisplay));
	}
}
