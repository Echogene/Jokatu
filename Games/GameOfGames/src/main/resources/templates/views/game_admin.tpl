layout 'views/game_view.tpl', true,

	additionalHeaders: contents {
		include template: 'components/button.tpl'
		include template: 'components/line.tpl'
		include template: 'components/game_entry.tpl'
		link(rel: 'stylesheet', href: '/css/game_admin.css')
	},

	mainContents: contents {
		gameNames.each { gameName ->
			button(
				is: 'j-button',
				id: "game_${gameName}",
				destination: "/topic/input.game.${gameId}",
				'data-input': "{\"gameName\": \"${gameName}\"}",
				"Create ${gameName}"
			)
		}
		yieldUnescaped markupGenerator.bindLast(
			tag: 'j-status',
			id: 'games',
			wrapperElement: 'j-game-entry',
			attributeName: 'data-game',
			destination: "/topic/games.game.${gameId}"
		)
	}