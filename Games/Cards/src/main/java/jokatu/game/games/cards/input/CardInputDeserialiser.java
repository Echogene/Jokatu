package jokatu.game.games.cards.input;

import jokatu.game.input.DeserialisationException;
import jokatu.game.input.InputDeserialiser;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Map;

import static jokatu.game.cards.Cards.CARDS_BY_DISPLAY;

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
		if (!CARDS_BY_DISPLAY.containsKey(cardDisplay)) {
			throw new DeserialisationException(json, "The value for 'card' was not a valid card.");
		}
		return new CardInput(CARDS_BY_DISPLAY.get(cardDisplay));
	}
}
