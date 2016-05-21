var JMessageBoxProto = Object.create(HTMLElement.prototype);

JMessageBoxProto.createdCallback = function() {
	/**
	 * @type {string}
	 * @private
	 */
	this._messageElementName = this.getAttribute('wrapperElement');
	var destination = this.getAttribute('destination');

	var initialData = JSON.parse(this.getAttribute('data-initial'));
	initialData.forEach(datum => this._createMessage(datum.payload, datum.headers));

	socket.subscribe(destination, this._createMessage.bind(this));
};

JMessageBoxProto._createMessage = function(body, headers) {
	var firstChar = this._messageElementName.charAt(0);
	var element;
	if (firstChar == firstChar.toUpperCase()) {
		// If the first character is uppercase, assume we want to wrap a JavaScript object.
		element = new window[this._messageElementName];
	} else {
		// Otherwise, we want to wrap a normal element.
		element = document.createElement(this._messageElementName);
	}
	element.className = 'message';
	if (typeof body === 'string') {
		element.textContent = body;
	} else if (body && typeof body === 'object' && typeof body.text === 'string') {
		element.textContent = body.text;
	}
	var attributeName = this.getAttribute('attributeName');
	if (attributeName) {
		element.setAttribute(attributeName, JSON.stringify({payload: body, headers: headers}));
	}
	this.appendChild(element);
};

var JMessageBox = document.registerElement('j-message-box', {
	prototype: JMessageBoxProto
});