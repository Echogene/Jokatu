import jokatu.game.games.noughtsandcrosses.input.NoughtOrCross

layout 'views/game_view.tpl', true,

	additionalHeaders: contents {
		include template: 'components/options.tpl'
	},

	mainContents: contents {
		'j-options'(
			id: 'allegiance',
			destination: "/topic/input.game.${gameId}",
			'data-options': "[{\"input\": {\"allegiance\": \"⭕\"}, \"display\": \"⭕\"}, {\"input\": {\"allegiance\": \"✕\"}, \"display\": \"✕\"}]"
		)
	}