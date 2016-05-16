function choose() {
	socket.send(`/topic/input.game.${gameId}`, {choice: document.getElementById('choice').value});
}