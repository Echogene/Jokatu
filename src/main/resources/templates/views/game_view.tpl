import static jokatu.util.Json.serialise

layout 'layouts/main.tpl', true,

	pageTitle: "Game $game.identifier",

	headers: contents {
		script(type: 'text/javascript', src: '/js/main.js') {}
		script(type: 'text/javascript', src: '/js/stomp.js') {}
		script(type: 'text/javascript', "game = ${serialise(game)};")
		script(type: 'text/javascript', src: '/js/game_main.js') {}
	},

	mainBody: contents {
		button(onclick: 'lol()', 'Subscribe')
		button(onclick: 'requestEvent()', 'Request event')
	}