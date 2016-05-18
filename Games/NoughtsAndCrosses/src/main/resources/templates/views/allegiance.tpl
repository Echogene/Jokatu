import jokatu.game.games.noughtsandcrosses.input.NoughtOrCross
import java.util.stream.Collectors

layout 'views/game_view.tpl', true,

	additionalHeaders: contents {
		include template: 'components/options.tpl'
	},

	mainContents: contents {
		'j-options'(
			id: 'allegiance',
			destination: "/topic/input.game.${gameId}",
			'data-options': Arrays.stream(NoughtOrCross.values())
					.map { allegiance -> "{\"input\": {\"allegiance\": \"${allegiance}\"}, \"display\": \"${allegiance}\"}" }
					.collect(Collectors.joining(', ', '[', ']'))
		)
	}