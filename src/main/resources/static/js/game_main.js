var socket = new Socket();
socket.subscribe(`/game/${game.identifier}`, handleMessage);

function join() {
	post('/joinGame.do', {gameID: game.identifier}, (game) => alert(JSON.stringify(game)));
}

function handleMessage(e) {
	var div = document.getElementById('lol');
	if (!div) {
		div = document.createElement('div');
		div.id = 'lol';
		document.body.appendChild(div);
	}
	div.innerHTML = JSON.stringify(e);
}

function choose() {
	socket.send(`/game/${game.identifier}`, document.getElementById('choice').value);
}