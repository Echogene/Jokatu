layout 'views/game_view.tpl', true,

	additionalHeaders: contents {
		include template: 'components/messagebox.tpl'
		include template: 'components/status.tpl'
		include template: 'components/button.tpl'
	},

	mainContents: contents {
		button(is: 'j-button', destination: "/topic/input.game.${game.identifier}", 'data-input': '{"join": true}', 'Join game')
	}