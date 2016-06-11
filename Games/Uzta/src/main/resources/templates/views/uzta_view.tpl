import static jokatu.util.Json.serialise

layout 'views/game_view.tpl', true,

	additionalHeaders: contents {
		include template: 'components/graph.tpl'
		link(rel: 'stylesheet', href: '/css/uzta.css')
	},

	mainContents: contents {
		yieldUnescaped markupGenerator.bindLast(
				tag: 'j-graph',
				id: 'graph',
				nodeElement: 'div',
				destination: "/topic/graph.game.${gameId}"
		)
	}