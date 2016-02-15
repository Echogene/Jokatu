function choose() {
	socket.send(`/topic/input.game.${game.identifier}`, document.getElementById('choice').value);
}