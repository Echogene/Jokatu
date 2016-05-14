import static jokatu.util.Json.serialise

layout 'layouts/main.tpl', true,

	pageTitle: "Game $game.identifier",

	headers: contents {
		script(type: 'text/javascript', "game = ${serialise(game)};")
		script(type: 'text/javascript', src: '/js/game_main.js') {}
		script(type: 'text/javascript', "socket.subscribe(\"/user/topic/errors.game.${game.identifier}\");")
		link(rel: 'stylesheet', href: '/css/game.css')
		additionalHeaders()
	},

	mainBody: contents {
		div(id: 'sidebar') {
			yieldUnescaped markupGenerator.bindLast(
					tag: "j-status",
					id: "status",
					wrapperElement: "div",
					destination: "/topic/status.game.${game.identifier}"
			)
			yieldUnescaped markupGenerator.bindHistory(
					tag: "j-message-box",
					id: "public-messages",
					wrapperElement: "div",
					destination: "/topic/public.game.${game.identifier}"
			)
		}
		div(id: 'contents') {
			mainContents()
		}
	}