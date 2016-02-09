function send(text) {
	socket.send(`/input/game/${game.identifier}`, text);
}