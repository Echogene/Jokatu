import static jokatu.util.Json.serialise

layout 'views/game_view.tpl', true,

	additionalHeaders: contents {
		include template: 'components/button.tpl'
		script(type: 'text/javascript', src: '/js/rock_paper_scissors.js') {}
	},

	mainContents: contents {
		button(is: 'j-button', destination: "/topic/input.game.${game.identifier}", 'data-input': '{"join": true}', 'Join game')
		select(id: 'choice') {
			option(value: 'ROCK', 'Rock')
			option(value: 'PAPER', 'Paper')
			option(value: 'SCISSORS', 'Scissors')
		}
		button(onclick: 'choose()', 'Choose')
	}