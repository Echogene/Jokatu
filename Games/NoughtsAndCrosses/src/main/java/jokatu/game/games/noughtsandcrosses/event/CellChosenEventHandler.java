package jokatu.game.games.noughtsandcrosses.event;

import jokatu.game.Game;
import jokatu.game.event.AnyGameEventHandler;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import static java.text.MessageFormat.format;

/**
 * Private events should be forwarded to the users they specify.
 * @author Steven Weston
 */
@Component
public class CellChosenEventHandler extends AnyGameEventHandler<CellChosenEvent> {

	@NotNull
	@Override
	protected Class<CellChosenEvent> getEventClass() {
		return CellChosenEvent.class;
	}

	@Override
	protected void handleCastGameAndEvent(@NotNull Game game, @NotNull CellChosenEvent event) {

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
