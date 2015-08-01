var socket = new Socket();

function lol() {
	socket.subscribe(`/game/${game.identifier}`, function(e) {
		var div = document.getElementById('lol');
		if (!div) {
			div = document.createElement('div');
			div.id = 'lol';
			document.body.appendChild(div);
		}
		div.innerHTML = JSON.stringify(e);
	});
}

function join() {
	post('/joinGame.do', {gameID: game.identifier}, function(game) {
		alert(JSON.stringify(game));
	});
}