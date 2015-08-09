function Socket() {
	this._ws = new WebSocket('ws://' + window.location.host + '/ws');
	this._ws.onmessage = this.onmessage.bind(this);

	this._subscribers = new Map();

	this._subscriptionId = 0;
}

Socket.prototype.onmessage = function(message) {
	var data = message.data;

	var commandEnd = data.indexOf('\n');
	var command = data.substring(0, commandEnd).trim();
	data = data.substring(commandEnd + 1);

	var headersEnd = data.indexOf('\n\n');
	var headers = data.substring(0, headersEnd)
		.split('\n')
		.reduce(
			(left, right) => {
				var keyEnd = right.indexOf(':');
				return left.set(right.substring(0, keyEnd).trim(), right.substring(keyEnd + 1).trim());
			},
			new Map()
	);
	data = data.substring(headersEnd + 2);

	var bodyEnd = data.indexOf('\0');
	if (headers.has('content-length')) {
		bodyEnd = Math.min(bodyEnd, headers.get('content-length'))
	}
	var body = data.substring(0, bodyEnd);
	if (headers.has('content-type') && headers.get('content-type').contains('application/json')) {
		body = JSON.parse(body);
	}

	// todo: support other commands when needed
	if (command === 'MESSAGE') {
		var subscriber = this._subscribers.get(headers.get('subscription'));
		if (subscriber) {
			subscriber(body, headers);
		}
	}
};

/**
 * @param {string} command
 * @param {Map} headers
 * @param {string|Object=} body
 * @private
 */
Socket.prototype._message = function(command, headers, body) {
	var message = `${command}\n`;

	for (var [key, value] of headers) {
		message += `${key}:${value}\n`;
	}
	message += '\n';

	if (typeof body === 'string') {
		message += body;
	} else if (typeof body !== 'undefined') {
		message += JSON.stringify(body);
	}

	message += '\0';

	this._ws.send(message)
};

/**
 * @param {string} destination
 * @param {function(Object, Map=)} callback
 */
Socket.prototype.subscribe = function(destination, callback) {

	var id = this._subscriptionId++;

	this._subscribers.set(`${id}`, callback);

	var headers = new Map();
	headers.set('destination', destination);
	headers.set('id', id);

	this._message('SUBSCRIBE', headers);
};

/**
 * @param {string} destination
 * @param {string|Object} body
 */
Socket.prototype.send = function(destination, body) {

	var headers = new Map();
	headers.set('destination', destination);

	this._message('SEND', headers, body);
};