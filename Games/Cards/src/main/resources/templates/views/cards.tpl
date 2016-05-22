import jokatu.game.cards.StandardCards

layout 'views/game_view.tpl', true,

	additionalHeaders: contents {
		link(rel: 'stylesheet', href: '/css/cards.css')
	},

	mainContents: contents {
		span(StandardCards.PRIVATE_CARD)
		StandardCards.ALL_CARDS.each { card ->
			span(card)
		}
	}