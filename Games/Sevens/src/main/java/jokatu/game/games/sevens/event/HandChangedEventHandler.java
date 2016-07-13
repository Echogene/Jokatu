package jokatu.game.games.sevens.event;

import jokatu.game.Game;
import jokatu.game.event.SpecificEventHandler;
import jokatu.game.games.sevens.player.SevensPlayer;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import static java.text.MessageFormat.format;

/**
 * Update the user's hand and inform everyone else that the number of cards in their hand has changed.
 * @author Steven Weston
 */
@Component
public class HandChangedEventHandler extends SpecificEventHandler<HandChangedEvent> {

	@NotNull
	@Override
	protected Class<HandChangedEvent> getEventClass() {
		return HandChangedEvent.class;
	}

	@Override
	protected void handleCastEvent(@NotNull Game<?> game, @NotNull HandChangedEvent event) {
		SevensPlayer player = event.getPlayer();
		sender.sendToUser(
				player.getName(),
				format("/topic/hand.game.{0}", game.getIdentifier()),
				player.getHand()
		);

		sender.send(
				format("/topic/handcount.game.{0}.{1}", game.getIdentifier(), player),
				player.getHand().size()
		);
	}
}
