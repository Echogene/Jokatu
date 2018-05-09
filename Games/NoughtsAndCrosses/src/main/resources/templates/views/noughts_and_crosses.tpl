import jokatu.game.games.noughtsandcrosses.input.NoughtOrCross

layout 'views/game_view.tpl', true,

	additionalHeaders: contents {
		include template: 'components/button.tpl'
		link(rel: 'stylesheet', href: '/css/noughts_and_crosses.css')
	},

	mainContents: contents {
		div(class: 'grid') {
			(0..8).each { cell ->
				yieldUnescaped markupGenerator.bindLast(
					tag: 'j-status',
					class: 'button',
					id: "cell_${cell}",
					wrapperElement: 'j-button',
					attributeName: 'data-contents',
					showAll: true,
					'data-defaultAttributes': "{\"destination\": \"/topic/input.game.${gameId}\", \"data-input\": {\"choice\": ${cell}}}",
					destination: "/topic/substatus.game.${gameId}.cell_${cell}"
				)
			}
		}
		yieldUnescaped markupGenerator.bindLast(
			tag: 'j-status',
			id: 'lines',
			wrapperElement: 'j-line',
			attributeName: 'data-ends',
			destination: "/topic/substatus.game.${gameId}.lines"
		)
	}