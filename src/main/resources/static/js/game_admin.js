function createGame() {
	var request = new XMLHttpRequest();
	request.open('get', 'createGame.do', true);
	request.setRequestHeader(_csrf_header, _csrf);
	request.onload = function() {
		var game = JSON.parse(this.responseText);
		games.push(game);
		renderGames([game]);
	};
	request.send();
}

function renderGames(games) {
	var gameContainer = document.getElementById('gameContainer');
	for (var i = 0; i < games.length; i++) {
		var game = games[i];
		var gameRow = document.createElement('div');
		gameRow.innerHTML = game.identifier;
		gameContainer.appendChild(gameRow);
	}
}