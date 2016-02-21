import static jokatu.util.Json.serialise

layout 'views/game_view.tpl', true,

	additionalHeaders: contents {
		include template: 'components/text.tpl'
		include template: 'components/messagebox.tpl'
		script(type: 'text/javascript', src: '/js/echo.js') {}
	},

	mainBody: contents {
		button(onclick: 'join()', 'Join game')
		'j-text'(id: 'text', submitName: 'Send', onSubmit: 'send') {}
		'j-message-box'(id: 'public-messages', wrapperElement: 'div', destination: "/topic/public.game.${game.identifier}") {}
		div(id: 'status', "${game.status.getText()}")
	}