var socket = new Socket();
socket.subscribe(`/topic/public.game.${game.identifier}`, handleMessage);
socket.subscribe(`/topic/status.game.${game.identifier}`, handleStatusChange);
socket.subscribe(`/user/topic/errors.game.${game.identifier}`, handleError);
socket.subscribe(`/user/topic/private.game.${game.identifier}`, handleMessage);

function join() {
	post('/joinGame.do', {gameID: game.identifier}, (newGame) => game = newGame, handleError);
}

function handleStatusChange(e) {
	game.status = e;
	document.getElementById('status').innerHTML = e;
}

function handleMessage(e) {
	var div = document.getElementById('lol');
	if (!div) {
		div = document.createElement('div');
		div.id = 'lol';
		document.body.appendChild(div);
	}
	div.innerHTML = JSON.stringify(e);
}

/**
 * @param {Object} e
 * @param {Map=} headers
 */
function handleError(e, headers) {
	var div = document.getElementById('error');
	if (!div) {
		div = document.createElement('div');
		div.id = 'error';
		document.body.appendChild(div);
	}
	if (headers && headers.has('subscription-id')) {
		div.innerHTML = 'Subscription error: ' + JSON.stringify(e.message);
		socket.unsubscribe(headers.get('subscription-id'));
	} else {
		div.innerHTML = 'Unknown error: ' + JSON.stringify(e.message);
	}
}