layout 'views/game_view.tpl', true,

	additionalHeaders: contents {
		include template: 'components/button.tpl'
		link(rel: 'stylesheet', href: '/css/cards.css')
	},

	mainContents: contents {
		yieldUnescaped markupGenerator.bindUserLast(
			tag: 'j-status',
			id: 'hand',
			wrapperElement: 'JButton',
			attributeName: 'data-input',
			attributeWrapper: '{\"card\": $status}',
			'data-wrapperAttributes': "{\"destination\": \"/topic/input.game.${gameId}\"}",
			destination: "/user/topic/hand.game.${gameId}",
			user: "${username}"
		)
	}