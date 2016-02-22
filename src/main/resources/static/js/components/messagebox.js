var JMessageBoxProto = Object.create(HTMLElement.prototype);

JMessageBoxProto.createdCallback = function() {

	var template = document.querySelector('#message_box_template');
	var clone = document.importNode(template.content, true);

	/**
	 * @type {string}
	 * @private
	 */
	this._messageElementName = this.getAttribute('wrapperElement');
	var destination = this.getAttribute('destination');

	/**
	 * @type {Element}
	 * @private
	 */
	this._container = clone.querySelector('div');

	var initialData = JSON.parse(this.getAttribute('data-initial'));
	initialData.forEach(this._createMessage.bind(this));

	socket.subscribe(destination, this._createMessage.bind(this));

	this.createShadowRoot().appendChild(clone);
};

JMessageBoxProto._createMessage = function(body) {
	var element = document.createElement(this._messageElementName);
	element.className = 'message';
	if (typeof body === 'string') {
		element.textContent = body;
	} else if (body && typeof body === 'object' && typeof body.text === 'string') {
		element.textContent = body.text;
	}
	element.setAttribute('data-message', JSON.stringify(body));
	this._container.appendChild(element);
};

var JMessageBox = document.registerElement('j-message-box', {
	prototype: JMessageBoxProto
});