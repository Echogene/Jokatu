import static jokatu.util.Json.serialise

layout 'views/game_view.tpl', true,

	additionalHeaders: contents {
		script(type: 'text/javascript', src: '/js/echo.js') {}
	},

	mainBody: contents {
		button(onclick: 'join()', 'Join game')
		input(id: 'text')
		button(onclick: 'send()', 'Send')
		div(id: 'status', "${game.status.getText()}")
	}