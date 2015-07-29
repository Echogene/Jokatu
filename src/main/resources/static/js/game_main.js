var ws = new WebSocket('ws://' + window.location.host + '/ws');

ws.onmessage = function(e) {
	var div = document.getElementById('lol');
	if (!div) {
		div = document.createElement('div');
		div.id = 'lol';
		document.body.appendChild(div);
	}
	div.innerHTML = e.data;
};

ws.onopen = function() {
	ws.send(subscribe('/game/' + game.identifier));
};