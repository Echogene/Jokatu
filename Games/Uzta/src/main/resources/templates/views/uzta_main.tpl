import static jokatu.util.Json.serialise
import jokatu.game.games.uzta.graph.NodeType

layout 'views/game_view.tpl', true,

	additionalHeaders: contents {
		include template: 'components/graph.tpl'
		include template: 'components/node.tpl'
		include template: 'components/edge.tpl'
		include template: 'components/button.tpl'
		include template: 'components/counter.tpl'
		include template: 'components/form/integer.tpl'
		include template: 'components/annotated.tpl'
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
							div(class: "${type}") {
								yieldUnescaped markupGenerator.bindLast(
									tag: 'j-counter',
									id: "${p.name}_${type}",
									class: "resource",
									limit: 10,
									wrapperElement: 'span',
									'data-defaultAttributes': "{\"text\": \"${type.getSymbol()}\"}",
									destination: "/topic/resource.game.${gameId}.${p.name}.${type}"
								)
								button(
									is: 'j-button',
									id: "trade_${p.name}_${type}",
									destination: "/topic/input.game.${gameId}",
									'data-input': "{\"resource\": \"${type}\", \"player\": \"${p.name}\"}",
									title: "Request ${type.plural} from ${p.name}.",
									'⇄'
								)
							}
						}
					}
					div(class: 'score') {
						span('Score: ')
						yieldUnescaped markupGenerator.bindLast(
							tag: 'j-status',
							id: "${p.name}_score",
							wrapperElement: 'j-annotated',
							'data-attributeMapping': '{\"data-popup\": \"annotation\", \"innerText\": \"number\"}',
							destination: "/topic/score.game.${gameId}.${p.name}"
						)
					}
				}
			}
		}

		yieldUnescaped markupGenerator.bindLast(
				tag: 'j-graph',
				id: 'graph',
				nodeElement: 'j-node',
				edgeElement: 'j-edge',
				'data-defaultEdgeAttributes': "{\"destination\": \"/topic/input.game.${gameId}\"}",
				destination: "/topic/graph.game.${gameId}"
		)

		div(id: 'me') {
			div(id: 'my_resources', class: "player ${player.colour}") {
				span(class: 'name', 'Your resources')
				div(class: 'resources') {
					NodeType.values().each { type ->
						div(class: "${type}") {
							yieldUnescaped markupGenerator.bindLast(
								tag: 'j-counter',
								id: "${username}_${type}",
								class: "resource",
								limit: 10,
								wrapperElement: 'span',
								'data-defaultAttributes': "{\"text\": \"${type.getSymbol()}\"}",
								destination: "/topic/resource.game.${gameId}.${username}.${type}"
							)
							button(
								is: 'j-button',
								id: "trade_${username}_${type}",
								destination: "/topic/input.game.${gameId}",
								'data-input': "{\"resource\": \"${type}\"}",
								title: "Request ${type.plural} from the supply.",
								'⇄'
							)
						}
					}
				}
				div(class: 'score') {
					span('Score: ')
					yieldUnescaped markupGenerator.bindLast(
						tag: 'j-status',
						id: "${username}_score",
						wrapperElement: 'j-annotated',
						'data-attributeMapping': '{\"data-popup\": \"annotation\", \"innerText\": \"number\"}',
						destination: "/topic/score.game.${gameId}.${username}"
					)
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