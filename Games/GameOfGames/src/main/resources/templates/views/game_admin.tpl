import jokatu.game.games.noughtsandcrosses.input.NoughtOrCross

layout 'views/game_view.tpl', true,

	additionalHeaders: contents {
		include template: 'components/button.tpl'
		include template: 'components/line.tpl'
		link(rel: 'stylesheet', href: '/css/noughts_and_crosses.css')
		script(type: 'text/javascript', src: '/js/noughts_and_crosses.js') {}
	},

	mainContents: contents {
		gameNames.each { gameName ->
			button(
				is: 'j-button',
				destination: "/topic/input.game.${gameId}",
				'data-input': "{\"gameName\": \"${gameName}\"}",
				"Create ${gameName}"
			)
		}
	}