import static jokatu.util.Json.serialise

layout 'layouts/main.tpl', true,

	pageTitle: 'Games',

	headers: contents {
		script(type: 'text/javascript', src: '/js/game_admin.js') {}
		script(type: 'text/javascript', "games = ${serialise(games)};")
	},

	mainBody: contents {
		div(id: 'gameContainer') {}
		script(type: 'text/javascript', 'renderGames(games);')
		select(id: 'gameName') {
			gameNames.each {
				option(value: it, it)
			}
		}
		button(id: 'create', onclick: 'createGame();', 'Create game')
	}