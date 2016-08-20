import static jokatu.util.Json.serialise
import jokatu.game.games.uzta.graph.NodeType

layout 'views/game_view.tpl', true,

	additionalHeaders: contents {
		include template: 'components/graph.tpl'
		include template: 'components/node.tpl'
		include template: 'components/edge.tpl'
		include template: 'components/button.tpl'
		include template: 'components/counter.tpl'
		link(rel: 'stylesheet', href: '/css/uzta.css')
		link(rel: 'stylesheet', href: '/css/uzta_main.css')
	},

	mainContents: contents {
		div(id: 'other_players') {
			otherPlayers.each { p ->
				div(class: "player ${p.colour}") {
					span(class: 'name', "${p.name}")
					div(class: 'resources') {
						NodeType.values().each { type ->
							yieldUnescaped markupGenerator.bindLast(
								tag: 'j-counter',
								id: "${p.name}_${type}",
								class: "resource ${type}",
								wrapperElement: 'span',
								'data-defaultAttributes': "{\"text\": \"${type.getSymbol()}\"}",
								destination: "/topic/resource.game.${gameId}.${p.name}.${type}"
							)
						}
					}
				}
			}
		}

		yieldUnescaped markupGenerator.bindLast(
				tag: 'j-graph',
				id: 'graph',
				nodeElement: 'JNode',
				edgeElement: 'JEdge',
				'data-defaultEdgeAttributes': "{\"destination\": \"/topic/input.game.${gameId}\"}",
				destination: "/topic/graph.game.${gameId}"
		)

		div(id: 'me') {
			div(id: 'my_resources', class: "player ${player.colour}") {
				span(class: 'name', 'Your resources')
				div(class: 'resources') {
					NodeType.values().each { type ->
						yieldUnescaped markupGenerator.bindLast(
							tag: 'j-counter',
							id: "${username}_${type}",
							class: "resource ${type}",
							wrapperElement: 'span',
							'data-defaultAttributes': "{\"text\": \"${type.getSymbol()}\"}",
							destination: "/topic/resource.game.${gameId}.${username}.${type}"
						)
					}
				}
			}

			button(
				is: 'j-button',
				id: 'end',
				destination: "/topic/input.game.${gameId}",
				'data-input': '{"skip": true}',
				'End turn'
			)
		}
	}