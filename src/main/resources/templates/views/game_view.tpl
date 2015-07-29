import static jokatu.util.Json.serialise

layout 'layouts/main.tpl', true,

	pageTitle: "Game $game.identifier",

	headers: contents {
		script(type: 'text/javascript', src: '/js/main.js') {}
		script(type: 'text/javascript', src: '/js/game_main.js') {}
		script(type: 'text/javascript', "game = ${serialise(game)};")
	},

	mainBody: contents {

	}