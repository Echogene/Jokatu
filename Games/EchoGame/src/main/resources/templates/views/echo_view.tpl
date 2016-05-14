import static jokatu.util.Json.serialise

layout 'views/game_view.tpl', true,

	additionalHeaders: contents {
		include template: 'components/text.tpl'
		include template: 'components/messagebox.tpl'
		include template: 'components/status.tpl'
		include template: 'components/button.tpl'
		script(type: 'text/javascript', src: '/js/echo.js') {}
	},

	mainContents: contents {
		'j-text'(id: 'text', submitName: 'Send', destination: "/topic/input.game.${game.identifier}") {}
		button(is: 'j-button', destination: "/topic/input.game.${game.identifier}", 'data-input': '{"text": "Echo!"}', 'Echo!')
	}