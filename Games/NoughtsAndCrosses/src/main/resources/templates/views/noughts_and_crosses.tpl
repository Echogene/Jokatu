import static jokatu.util.Json.serialise

layout 'views/game_view.tpl', true,

	additionalHeaders: contents {
		include template: 'components/messagebox.tpl'
		include template: 'components/status.tpl'
		include template: 'components/button.tpl'
		link(rel: 'stylesheet', href: '/css/noughts_and_crosses.css')
		script(type: 'text/javascript', src: '/js/noughts_and_crosses.js') {}
	},

	mainBody: contents {
		button(is: 'j-button', destination: "/topic/input.game.${game.identifier}", 'data-input': '{"join": true}', 'Join game')
		div(class: 'grid') {
			(1..9).each { cell ->
				button(is: 'j-button', destination: "/topic/input.game.${game.identifier}", 'data-input': "{\"choice\": ${cell}}") {
					yieldUnescaped markupGenerator.bindLast(
						tag: "j-status", id: "cell_${cell}_status",
						wrapperElement: "div", destination: "/topic/substatus.game.${game.identifier}.cell_${cell}"
					)
				}
			}
		}
	}