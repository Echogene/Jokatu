package jokatu.game.event;

import jokatu.components.config.InputDeserialisers;
import jokatu.game.Game;
import jokatu.game.exception.GameException;
import jokatu.game.input.DeserialisationException;
import jokatu.game.input.Input;
import jokatu.game.input.InputDeserialiser;
import jokatu.components.ui.DialogRequestor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DialogRequestEventHandler extends SpecificEventHandler<DialogRequest.DialogRequestEvent> {

	private final DialogRequestor dialogRequestor;
	private final InputDeserialisers inputDeserialisers;

	@Autowired
	public DialogRequestEventHandler(DialogRequestor dialogRequestor, InputDeserialisers inputDeserialisers) {
		this.dialogRequestor = dialogRequestor;
		this.inputDeserialisers = inputDeserialisers;
	}

	@NotNull
	@Override
	protected Class<DialogRequest.DialogRequestEvent> getEventClass() {
		return DialogRequest.DialogRequestEvent.class;
	}

	@Override
	protected void handleCastEvent(@NotNull Game<?> game, @NotNull DialogRequest.DialogRequestEvent event) {
		dialogRequestor.requestDialog(
				event.getDialog(),
				event.getPlayer().getName(),
				game.getIdentifier(),
				json -> {
					InputDeserialiser<?> deserialiser = inputDeserialisers.getDeserialiser(event.getInputClass());
					if (deserialiser == null) {
						throw new GameException(
								game.getIdentifier(),
								"Could not find deserialiser for {0} while responding to dialog.",
								event.getInputClass().getSimpleName()
						);
					}
					Input input = null;
					try {
						input = deserialiser.deserialise(json);
						event.accept(input);
					} catch (Exception e) {
						throw new GameException(
								game.getIdentifier(),
								e,
								e.getMessage()
						);
					}
				}
		);
	}
}
