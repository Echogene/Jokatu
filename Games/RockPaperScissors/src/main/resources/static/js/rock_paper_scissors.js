function choose() {
	socket.send(`/topic/input.game.${game.identifier}`, {choice: document.getElementById('choice').value});
}