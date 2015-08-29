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
		button(onclick: 'join()', 'Join game')
		// todo: obviously factor this out
		select(id: 'choice') {
			option(value: 'ROCK', 'Rock')
			option(value: 'PAPER', 'Paper')
			option(value: 'SCISSORS', 'Scissors')
		}
		button(onclick: 'choose()', 'Choose')
		div(id: 'status', "${game.status.getText()}")
	}