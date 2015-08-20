var socket = new Socket();
socket.subscribe(`/public/game/${game.identifier}`, handleMessage);

function join() {
	socket.subscribe(`/user/${username}/game/${game.identifier}`, handleMessage);
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
	socket.send(`/input/game/${game.identifier}`, document.getElementById('choice').value);
}