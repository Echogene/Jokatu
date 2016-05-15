import static jokatu.util.Json.serialise

layout 'layouts/main.tpl', true,

	pageTitle: "Game $game.identifier",

	headers: contents {
		script(type: 'text/javascript', "game = ${serialise(game)};")
		script(type: 'text/javascript', src: '/js/game_main.js') {}
		script(type: 'text/javascript', "socket.subscribe(\"/user/topic/errors.game.${game.identifier}\");")
		link(rel: 'stylesheet', href: '/css/game.css')
		include template: 'components/messagebox.tpl'
		include template: 'components/status.tpl'
		include template: 'components/player.tpl'
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
			yieldUnescaped markupGenerator.bindLast(
					tag: 'j-status',
					id: 'players',
					wrapperElement: 'JPlayer',
					removeOldChildren: 'true',
					destination: "/topic/players.game.${game.identifier}"
			)
			yieldUnescaped markupGenerator.bindLast(
					tag: 'j-status',
					id: 'observers',
					wrapperElement: 'div',
					removeOldChildren: 'true',
					destination: "/topic/observers.game.${game.identifier}"
			)
		}
		div(id: 'contents') {
			mainContents()
		}
	}