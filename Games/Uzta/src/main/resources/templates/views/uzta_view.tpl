import static jokatu.util.Json.serialise

layout 'views/game_view.tpl', true,

	additionalHeaders: contents {
		include template: 'components/graph.tpl'
	},

	mainContents: contents {
		yieldUnescaped markupGenerator.bindLast(
				tag: 'j-graph',
				id: 'graph',
				wrapperElement: 'div',
				destination: "/topic/graph.game.${gameId}"
		)
	}