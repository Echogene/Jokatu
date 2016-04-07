var JStatusProto = Object.create(HTMLElement.prototype);

JStatusProto.createdCallback = function() {

	this._statusElementName = this.getAttribute('wrapperElement');
	var destination = this.getAttribute('destination');

	this._shadowRoot = this.createShadowRoot();

	var initialData = JSON.parse(this.getAttribute('data-initial'));
	this._setStatus(initialData);

	socket.subscribe(destination, this._setStatus.bind(this));
};

JStatusProto._setStatus = function(statuses) {
	if (!(statuses instanceof Array)) {
		statuses = [statuses];
	}
	this._ensureEnoughElements(statuses.length);
	for (var i = 0; i < this._shadowRoot.childNodes.length; i++) {
		var element = this._shadowRoot.childNodes[i];
		var status = statuses[i];
		if (status) {
			this._updateElement(element, status);
		} else {
			// Hide the element.
			element.style.display = 'none';
		}
	}
};

JStatusProto._ensureEnoughElements = function(number) {
	var elementsNeeded = number - this._shadowRoot.childNodes.length;
	if (elementsNeeded > 0) {
		for (var i = 0; i < elementsNeeded; i++) {
			this._shadowRoot.appendChild(document.createElement(this._statusElementName));
		}
	}
};

JStatusProto._updateElement = function(element, status) {
	// Show the element.
	element.style.display = '';
	if (typeof status === 'string') {
		element.textContent = status;
	} else if (status && typeof status === 'object' && typeof status.text === 'string') {
		element.textContent = status.text;
	}
	element.setAttribute('data-status', JSON.stringify(status));
};

var JStatus = document.registerElement('j-status', {
	prototype: JStatusProto
});