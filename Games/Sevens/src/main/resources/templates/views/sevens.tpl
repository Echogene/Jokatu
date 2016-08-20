import jokatu.game.cards.Suit
import jokatu.game.cards.Cards

layout 'views/game_view.tpl', true,

	additionalHeaders: contents {
		include template: 'components/card.tpl'
		include template: 'components/counter.tpl'
		link(rel: 'stylesheet', href: '/css/sevens.css')
	},

	mainContents: contents {
		div(id: 'others') {
			otherPlayers.each { p ->
				div() {
					span("${p.name}")
					yieldUnescaped markupGenerator.bindLast(
						tag: 'j-counter',
						id: "hand_${p.name}",
						class: 'hand',
						wrapperElement: 'span',
						'data-defaultAttributes': "{\"text\": \"${Cards.PRIVATE_CARD}\"}",
						destination: "/topic/handcount.game.${gameId}.${p.name}"
					)
				}
			}
		}
		div(id: 'board') {
			Suit.values().each { suit ->
				yieldUnescaped markupGenerator.bindLast(
					tag: 'j-status',
					id: "suit_${suit}",
					class: 'suit',
					wrapperElement: 'div',
					destination: "/topic/substatus.game.${gameId}.${suit}"
				)
			}
		}
		div(id: 'player') {
			button(
				is: 'j-button',
				id: 'skip',
				destination: "/topic/input.game.${gameId}",
				'data-input': '{"skip": true}',
				'Skip'
			)
			yieldUnescaped markupGenerator.bindUserLast(
				tag: 'j-status',
				id: 'hand',
				wrapperElement: 'JCard',
				attributeName: 'data-card',
				removeOldChildren: true,
				'data-defaultAttributes': "{\"destination\": \"/topic/input.game.${gameId}\"}",
				destination: "/user/topic/hand.game.${gameId}",
				user: "${username}"
			)
		}
	}