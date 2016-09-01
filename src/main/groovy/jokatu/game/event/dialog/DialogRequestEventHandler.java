package jokatu.game.event.dialog;

import jokatu.components.config.InputDeserialisers;
import jokatu.components.ui.DialogRequestor;
import jokatu.game.Game;
import jokatu.game.event.SpecificEventHandler;
import jokatu.game.exception.GameException;
import jokatu.game.input.DeserialisationException;
import jokatu.game.input.Input;
import jokatu.game.input.InputDeserialiser;
import jokatu.game.input.acknowledge.AcknowledgeInput;
import jokatu.game.input.acknowledge.AcknowledgeInputDeserialiser;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DialogRequestEventHandler extends SpecificEventHandler<DialogRequest> {

	private final DialogRequestor dialogRequestor;
	private final InputDeserialisers inputDeserialisers;
	private final AcknowledgeInputDeserialiser acknowledgeInputDeserialiser;

	@Autowired
	public DialogRequestEventHandler(
			DialogRequestor dialogRequestor,
			InputDeserialisers inputDeserialisers,
			AcknowledgeInputDeserialiser acknowledgeInputDeserialiser
	) {
		this.dialogRequestor = dialogRequestor;
		this.inputDeserialisers = inputDeserialisers;
		this.acknowledgeInputDeserialiser = acknowledgeInputDeserialiser;
	}

	@NotNull
	@Override
	protected Class<DialogRequest> getEventClass() {
		return DialogRequest.class;
	}

	@Override
	protected void handleCastEvent(@NotNull Game<?> game, @NotNull DialogRequest event) {
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
					try {
						AcknowledgeInput acknowledgeInput = acknowledgeInputDeserialiser.deserialise(json);
						if (!acknowledgeInput.isAcknowledgement()) {
							// todo: add a way to handle cancels on the dialog request
							return;
						}
					} catch (DeserialisationException ignore) {}

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
