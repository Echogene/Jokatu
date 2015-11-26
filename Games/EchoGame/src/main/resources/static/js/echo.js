function send() {
	socket.send(`/input/game/${game.identifier}`, document.getElementById('text').value);
}