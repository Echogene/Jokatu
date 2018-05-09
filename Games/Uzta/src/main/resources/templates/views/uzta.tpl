import static jokatu.util.Json.serialise

layout 'views/game_view.tpl', true,

	additionalHeaders: contents {
		include template: 'components/graph.tpl'
		include template: 'components/node.tpl'
		include template: 'components/edge.tpl'
		include template: 'components/button.tpl'
		link(rel: 'stylesheet', href: '/css/uzta.css')
	},

	mainContents: contents {
		yieldUnescaped markupGenerator.bindLast(
				tag: 'j-graph',
				id: 'graph',
				nodeElement: 'j-node',
				edgeElement: 'j-edge',
				'data-defaultEdgeAttributes': "{\"destination\": \"/topic/input.game.${gameId}\"}",
				destination: "/topic/graph.game.${gameId}"
		)
	}