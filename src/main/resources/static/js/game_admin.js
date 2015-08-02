function createGame() {
	post('createGame.do', {}, function(game) {
		games.push(game);
		renderGames([game]);
	});
}

function renderGames(games) {
	var gameContainer = document.getElementById('gameContainer');
	for (var i = 0; i < games.length; i++) {
		var game = games[i];
		var gameRow = document.createElement('div');
		gameRow.innerHTML = '<a href="game/' + game.identifier + '">' + game.identifier + '</a>';
		gameContainer.appendChild(gameRow);
	}
}