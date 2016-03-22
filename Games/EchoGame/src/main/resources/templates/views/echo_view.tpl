import static jokatu.util.Json.serialise

layout 'views/game_view.tpl', true,

	additionalHeaders: contents {
		include template: 'components/text.tpl'
		include template: 'components/messagebox.tpl'
		include template: 'components/status.tpl'
		include template: 'components/button.tpl'
		script(type: 'text/javascript', src: '/js/echo.js') {}
	},

	mainBody: contents {
		'j-text'(id: 'text', submitName: 'Send', destination: "/topic/input.game.${game.identifier}") {}
		button(is: 'j-button', destination: "/topic/input.game.${game.identifier}", 'data-input': '{"text": "Echo!"}', 'Echo!')
		yieldUnescaped markupGenerator.bindHistory(
				tag: "j-message-box", id: "public-messages",
				wrapperElement: "div", destination: "/topic/public.game.${game.identifier}"
		)
		yieldUnescaped markupGenerator.bindLast(
				tag: "j-status", id: "status",
				wrapperElement: "div", destination: "/topic/status.game.${game.identifier}"
		)
	}