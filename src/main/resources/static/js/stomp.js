function Socket() {
	/**
	 * @type {WebSocket}
	 * @private
	 */
	this._ws = new WebSocket(`ws://${window.location.host}/ws`);
	this._ws.onmessage = this.onmessage.bind(this);
	/**
	 * @type {Array.<String>}
	 * @private
	 */
	this._queue = [];

	this._ws.onopen = () => {
		this._message("CONNECT");
	};

	/**
	 * @type {Map}
	 * @private
	 */
	this._subscribers = new Map();

	/**
	 * @type {Map}
	 * @private
	 */
	this._receiptHandlers = new Map();

	/**
	 * @type {number}
	 * @private
	 */
	this._currentId = 0;
}

Socket.prototype.onmessage = function(message) {
	var data = message.data;

	if (data.trim().length === 0) {
		return;
	}

	console.log(`Received:\n${data}`);

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
	if (headers.has('content-type') && headers.get('content-type').includes('application/json')) {
		body = JSON.parse(body);
	}
	
	var receiptId = headers.get('receipt-id');
	var onReceipt = this._receiptHandlers.get(receiptId);

	// todo: support other commands when needed
	if (command === 'CONNECTED') {
		this._onConnect();

	} else if (command === 'MESSAGE') {
		var subscribers = this._subscribers.get(headers.get('destination'));
		if (subscribers) {
			subscribers.forEach((subscriber) => subscriber(body, headers));
		}
		if (onReceipt) {
			onReceipt._errorCallback(body, headers);
		}
	} else if (command === 'RECEIPT') {
		if (onReceipt) {
			onReceipt._callback();
		}
	}
};

Socket.prototype._onConnect = function() {
	this._queue.forEach((message) => this._ws.send(message));
};

Socket.ReceiptHandler = function() {
	this._callback = () => {};
	this._errorCallback = () => {};
};

Socket.ReceiptHandler.prototype.then = function(callback) {
	this._callback = callback;
	return this;
};

Socket.ReceiptHandler.prototype.catch = function(callback) {
	this._errorCallback = callback;
	return this;
};

/**
 * @param {string} command
 * @param {Map=} headers
 * @param {string|Object=} body
 * @returns {Socket.ReceiptHandler|undefined}
 * @private
 */
Socket.prototype._message = function(command, headers, body) {
	var message = `${command}\n`;

	if (command !== 'CONNECT') {
		var receiptId = this._getNextId();
		var receiptHandler = new Socket.ReceiptHandler();
		this._receiptHandlers.set(`${receiptId}`, receiptHandler);
		headers.set('receipt', receiptId);
	}

	if (typeof headers !== 'undefined') {
		for (var [key, value] of headers) {
			message += `${key}:${value}\n`;
		}
	}
	message += '\n';

	if (typeof body !== 'undefined') {
		message += JSON.stringify(body);
	}

	message += '\0';

	console.log(`Sending:\n${message}`);

	if (this._ws.readyState == WebSocket.OPEN) {
		this._ws.send(message);
	} else {
		this._queue.push(message);
	}

	return receiptHandler;
};

/**
 * @returns {number}
 * @private
 */
Socket.prototype._getNextId = function() {
	return this._currentId++;
};

/**
 * @param {string} destination
 * @param {function(Object, Map=)=} callback
 * @returns {Socket.ReceiptHandler}
 */
Socket.prototype.subscribe = function(destination, callback) {

	if (this._subscribers.has(destination) && callback) {
		this._subscribers.get(destination).push(callback);
	} else {
		var id = this._getNextId();

		if (callback) {
			this._subscribers.set(destination, [callback]);
		}

		var headers = new Map();
		headers.set('destination', destination);
		headers.set('id', id);

		return this._message('SUBSCRIBE', headers, undefined);
	}
};

/**
 * @param {string} subscriptionId
 * @returns {Socket.ReceiptHandler}
 */
Socket.prototype.unsubscribe = function(subscriptionId) {

	this._subscribers.delete(subscriptionId);

	var headers = new Map();
	headers.set('id', subscriptionId);

	return this._message('UNSUBSCRIBE', headers, undefined);
};

/**
 * @param {string} destination
 * @param {string|Object} body
 * @returns {Socket.ReceiptHandler}
 */
Socket.prototype.send = function(destination, body) {

	var headers = new Map();
	headers.set('destination', destination);
	headers.set('content-type', 'application/json');

	return this._message('SEND', headers, body);
};

var socket = new Socket();