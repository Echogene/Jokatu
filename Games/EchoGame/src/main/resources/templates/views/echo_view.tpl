import static jokatu.util.Json.serialise

layout 'views/game_view.tpl', true,

	additionalHeaders: contents {
		script(type: 'text/javascript', src: '/js/echo.js') {}
		script(type: 'text/javascript', src: '/js/components/text.js') {}
	},

	mainBody: contents {
		button(onclick: 'join()', 'Join game')
		'j-text'(id: 'text', submitName: 'Send', onSubmit: 'send') {}
		div(id: 'status', "${game.status.getText()}")
	}