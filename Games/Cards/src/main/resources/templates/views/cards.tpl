import jokatu.game.cards.Suit

layout 'views/game_view.tpl', true,

	additionalHeaders: contents {
		include template: 'components/card.tpl'
		link(rel: 'stylesheet', href: '/css/cards.css')
	},

	mainContents: contents {
		div(id: 'board') {
			Suit.values().each { suit ->
				yieldUnescaped markupGenerator.bindLast(
					tag: 'j-status',
					id: "suit_${suit}",
					class: 'suit',
					wrapperElement: 'JCard',
					attributeName: 'data-card',
					destination: "/topic/substatus.game.${gameId}.${suit}"
				)
			}
		}
		yieldUnescaped markupGenerator.bindUserLast(
			tag: 'j-status',
			id: 'hand',
			wrapperElement: 'JCard',
			attributeName: 'data-card',
			'data-wrapperAttributes': "{\"destination\": \"/topic/input.game.${gameId}\"}",
			destination: "/user/topic/hand.game.${gameId}",
			user: "${username}"
		)
	}