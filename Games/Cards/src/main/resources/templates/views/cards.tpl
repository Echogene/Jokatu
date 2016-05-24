layout 'views/game_view.tpl', true,

	additionalHeaders: contents {
		link(rel: 'stylesheet', href: '/css/cards.css')
	},

	mainContents: contents {
		yieldUnescaped markupGenerator.bindUserLast(
			tag: 'j-status',
			id: 'hand',
			wrapperElement: 'span',
			attributeName: 'data-hand',
			destination: "/user/topic/hand.game.${gameId}",
			user: "${username}"
		)
	}