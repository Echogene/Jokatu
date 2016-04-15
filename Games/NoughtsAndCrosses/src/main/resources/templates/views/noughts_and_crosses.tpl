import static jokatu.util.Json.serialise

layout 'views/game_view.tpl', true,

	additionalHeaders: contents {
		include template: 'components/messagebox.tpl'
		include template: 'components/status.tpl'
		include template: 'components/button.tpl'
		include template: 'components/line.tpl'
		include template: 'components/errormessage.tpl'
		link(rel: 'stylesheet', href: '/css/noughts_and_crosses.css')
		script(type: 'text/javascript', src: '/js/noughts_and_crosses.js') {}
	},

	mainBody: contents {
		button(is: 'j-button', destination: "/topic/input.game.${game.identifier}", 'data-input': '{"join": true}', 'Join game')
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
		yieldUnescaped markupGenerator.bindUserHistory(
				tag: "j-message-box",
				id: "errors",
				wrapperElement: "JErrorMessage",
				destination: "/user/topic/errors.game.${game.identifier}",
				user: "${username}"
		)
	}