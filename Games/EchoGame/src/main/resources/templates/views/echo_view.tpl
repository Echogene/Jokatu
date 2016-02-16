import static jokatu.util.Json.serialise

layout 'views/game_view.tpl', true,

	additionalHeaders: contents {
		include template: 'components/text.tpl'
		script(type: 'text/javascript', src: '/js/echo.js') {}
	},

	mainBody: contents {
		button(onclick: 'join()', 'Join game')
		'j-text'(id: 'text', submitName: 'Send', onSubmit: 'send') {}
		div(id: 'status', "${game.status.getText()}")
	}