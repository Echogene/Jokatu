import com.fasterxml.jackson.databind.ObjectMapper

layout 'layouts/main.tpl', true,

	pageTitle: 'Games',

	headers: contents {
		script(type: 'text/javascript', src: '/js/game_admin.js') {}
		script(type: 'text/javascript', "games = ${new ObjectMapper().writeValueAsString(games)};")
	},

	mainBody: contents {
		div(id: 'gameContainer') {}
		script(type: 'text/javascript', 'renderGames(games);')
		button(id: 'create', onclick: 'createGame();', 'Create game')
	}