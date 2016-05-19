import jokatu.game.games.rockpaperscissors.game.RockPaperScissors
import java.util.stream.Collectors

layout 'views/game_view.tpl', true,

	additionalHeaders: contents {
		include template: 'components/options.tpl'
	},

	mainContents: contents {
		'j-options'(
			id: 'choice',
			destination: "/topic/input.game.${gameId}",
			'data-options': Arrays.stream(RockPaperScissors.values())
					.map { rps -> "{\"input\": {\"choice\": \"${rps}\"}, \"display\": \"${rps}\"}" }
					.collect(Collectors.joining(', ', '[', ']'))
		)
	}