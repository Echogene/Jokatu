package jokatu.game.games.noughtsandcrosses.event;

import jokatu.components.stomp.StoringMessageSender;
import jokatu.game.Game;
import jokatu.game.event.EventHandler;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.text.MessageFormat.format;

/**
 * Private events should be forwarded to the users they specify.
 * @author Steven Weston
 */
@Component
public class CellChosenEventHandler extends EventHandler<CellChosenEvent> {

	private final StoringMessageSender sender;

	@Autowired
	public CellChosenEventHandler(StoringMessageSender sender) {
		this.sender = sender;
	}

	@NotNull
	@Override
	protected Class<CellChosenEvent> getEventClass() {
		return CellChosenEvent.class;
	}

	@Override
	protected void handleCastEvent(@NotNull Game game, @NotNull CellChosenEvent event) {

		sender.send(
				format("/topic/substatus.game.{0}.cell_{1}", game.getIdentifier(), event.getCell()),
				event.getMessage()
		);
		sender.send(
				format("/topic/substatus.game.{0}.lines", game.getIdentifier()),
				event.getLines()
		);
	}
}
