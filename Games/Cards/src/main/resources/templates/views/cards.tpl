import jokatu.game.cards.StandardCard

layout 'views/game_view.tpl', true,

	additionalHeaders: contents {
		link(rel: 'stylesheet', href: '/css/cards.css')
	},

	mainContents: contents {
		span(StandardCard.PRIVATE_CARD)
		StandardCard.ALL_CARDS.each { card ->
			span(card)
		}
	}