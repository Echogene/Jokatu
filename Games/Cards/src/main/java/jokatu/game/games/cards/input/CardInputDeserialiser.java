package jokatu.game.games.cards.input;

import jokatu.game.input.InputDeserialiser;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CardInputDeserialiser implements InputDeserialiser<CardInput> {

	@NotNull
	@Override
	public CardInput deserialise(@NotNull Map<String, Object> json) {
		return new CardInput((String) json.get("text"));
	}
}
