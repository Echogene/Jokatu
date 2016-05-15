import jokatu.game.games.noughtsandcrosses.input.NoughtOrCross

layout 'views/game_view.tpl', true,

	additionalHeaders: contents {
		include template: 'components/button.tpl'
		include template: 'components/line.tpl'
		link(rel: 'stylesheet', href: '/css/noughts_and_crosses.css')
		script(type: 'text/javascript', src: '/js/noughts_and_crosses.js') {}
	},

	mainContents: contents {
		div(class: 'grid') {
			(0..8).each { cell ->
				yieldUnescaped markupGenerator.bindLast(
					tag: 'j-status',
					class: 'button',
					id: "cell_${cell}",
					wrapperElement: 'JButton',
					showAll: true,
					'data-wrapperAttributes': "{\"destination\": \"/topic/input.game.${game.identifier}\", \"data-input\": {\"choice\": ${cell}}}",
					destination: "/topic/substatus.game.${game.identifier}.cell_${cell}"
				)
			}
		}
		yieldUnescaped markupGenerator.bindLast(
			tag: "j-status",
			id: "lines",
			wrapperElement: "JLine",
			destination: "/topic/substatus.game.${game.identifier}.lines"
		)
	}