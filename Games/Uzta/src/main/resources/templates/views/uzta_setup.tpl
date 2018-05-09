import static jokatu.util.Json.serialise

layout 'views/game_view.tpl', true,

	additionalHeaders: contents {
		include template: 'components/graph.tpl'
		include template: 'components/node.tpl'
		include template: 'components/button.tpl'
		link(rel: 'stylesheet', href: '/css/uzta.css')
	},

	mainContents: contents {
		yieldUnescaped markupGenerator.bindLast(
				tag: 'j-graph',
				id: 'graph',
				nodeElement: 'j-node',
				destination: "/topic/graph.game.${gameId}"
		)
		button(
			is: 'j-button',
			id: 'randomise',
			destination: "/topic/input.game.${gameId}",
			'data-input': '{"randomise": true}',
			'Randomise graph'
		)
		button(
			is: 'j-button',
			id: 'finish',
			destination: "/topic/input.game.${gameId}",
			'data-input': '{"end": true}',
			'Finish setup'
		)
	}