layout 'views/game_view.tpl', true,

	additionalHeaders: contents {
		include template: 'components/messagebox.tpl'
		include template: 'components/status.tpl'
		include template: 'components/button.tpl'
	},

	mainBody: contents {
		button(is: 'j-button', destination: "/topic/input.game.${game.identifier}", 'data-input': '{"join": true}', 'Join game')
		yieldUnescaped markupGenerator.bindHistory(
				tag: "j-message-box",
				id: "public-messages",
				wrapperElement: "div",
				destination: "/topic/public.game.${game.identifier}"
		)
		yieldUnescaped markupGenerator.bindLast(
				tag: "j-status",
				id: "status",
				wrapperElement: "div",
				destination: "/topic/status.game.${game.identifier}"
		)
	}