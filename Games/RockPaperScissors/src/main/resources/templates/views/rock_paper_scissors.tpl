import static jokatu.util.Json.serialise

layout 'views/game_view.tpl', true,

	additionalHeaders: contents {
		script(type: 'text/javascript', src: '/js/rock_paper_scissors.js') {}
	},

	mainBody: contents {
		button(onclick: 'join()', 'Join game')
		select(id: 'choice') {
			option(value: 'ROCK', 'Rock')
			option(value: 'PAPER', 'Paper')
			option(value: 'SCISSORS', 'Scissors')
		}
		button(onclick: 'choose()', 'Choose')
		div(id: 'status', "${game.status.getText()}")
	}