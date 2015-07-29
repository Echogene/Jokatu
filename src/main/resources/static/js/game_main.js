var ws = new WebSocket('ws://' + window.location.host + '/ws');

ws.onopen = function() {
	ws.send(subscribe('/game/' + game.identifier));
};