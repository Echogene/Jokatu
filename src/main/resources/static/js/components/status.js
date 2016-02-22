var JStatusProto = Object.create(HTMLElement.prototype);

JStatusProto.createdCallback = function() {

	var statusElementName = this.getAttribute('wrapperElement');
	var destination = this.getAttribute('destination');

	/**
	 * @type {Element}
	 * @private
	 */
	this._element = document.createElement(statusElementName);

	var initialData = JSON.parse(this.getAttribute('data-initial'));
	this._setStatus(initialData);

	socket.subscribe(destination, this._setStatus.bind(this));

	this.createShadowRoot().appendChild(this._element);
};

JStatusProto._setStatus = function(body) {
	if (typeof body === 'string') {
		this._element.textContent = body;
	} else if (body && typeof body === 'object' && typeof body.text === 'string') {
		this._element.textContent = body.text;
	}
	this._element.setAttribute('data-status', JSON.stringify(body));
};

var JStatus = document.registerElement('j-status', {
	prototype: JStatusProto
});