function send(text) {
	return socket.send(`/topic/input.game.${game.identifier}`, text);
}