package jokatu.game.games.sevens.input;

import jokatu.game.input.DeserialisationException;
import jokatu.game.input.TypedSingleKeyInputDeserialiser;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Map;

import static jokatu.game.cards.Cards.CARDS_BY_DISPLAY;

@Component
public class CardInputDeserialiser extends TypedSingleKeyInputDeserialiser<String, CardInput> {

	@NotNull
	@Override
	public CardInput deserialiseTypedSingleValue(@NotNull Map<String, Object> json, @NotNull String cardDisplay) throws DeserialisationException {
		if (!CARDS_BY_DISPLAY.containsKey(cardDisplay)) {
			throw new DeserialisationException(json, "The value for 'card' was not a valid card.");
		}
		return new CardInput(CARDS_BY_DISPLAY.get(cardDisplay));
	}

	@NotNull
	@Override
	protected Class<String> getType() {
		return String.class;
	}

	@NotNull
	@Override
	protected String getKeyName() {
		return "card";
	}
}
