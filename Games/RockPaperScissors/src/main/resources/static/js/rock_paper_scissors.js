function choose() {
	socket.send(`/input.game.${game.identifier}`, document.getElementById('choice').value);
}