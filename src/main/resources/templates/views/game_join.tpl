layout 'views/game_view.tpl', true,

	additionalHeaders: contents {
		include template: 'components/messagebox.tpl'
		include template: 'components/status.tpl'
		include template: 'components/button.tpl'
		link(rel: 'stylesheet', href: '/css/join.css')
	},

	mainContents: contents {
		button(
			is: 'j-button',
			id: 'join',
			destination: "/topic/input.game.${gameId}",
			'data-input': '{"join": true}',
			'Join game'
		)
	}