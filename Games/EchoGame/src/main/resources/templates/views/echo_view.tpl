import static jokatu.util.Json.serialise

layout 'views/game_view.tpl', true,

	additionalHeaders: contents {
		include template: 'components/text.tpl'
		include template: 'components/button.tpl'
		script(type: 'text/javascript', src: '/js/echo.js') {}
	},

	mainContents: contents {
		'j-text'(id: 'text', submitLabel: 'Send', destination: "/topic/input.game.${gameId}") {}
		button(is: 'j-button', destination: "/topic/input.game.${gameId}", 'data-input': '{"text": "Echo!"}', 'Echo!')
	}