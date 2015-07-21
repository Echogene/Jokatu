layout 'layouts/main.tpl', true,

	pageTitle: 'Games',

	headers: contents {
		script(type: 'text/javascript', src: '/js/game_admin.js') {}
	},

	mainBody: contents {
		div(id: "games") {
			games.each { game ->
				div("${game.identifier}")
			}
		}
		button(id: 'create', onclick: 'createGame()', 'Create game')
	}