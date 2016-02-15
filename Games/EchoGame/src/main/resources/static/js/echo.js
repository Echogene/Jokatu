function send(text) {
	socket.send(`/topic/input.game.${game.identifier}`, text);
}