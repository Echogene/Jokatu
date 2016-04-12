var JStatusProto = Object.create(HTMLElement.prototype);

JStatusProto.createdCallback = function() {

	this._statusElementName = this.getAttribute('wrapperElement');
	this._showAll = this.getAttribute('showAll');
	var destination = this.getAttribute('destination');

	/**
	 * Attributes to set on the status elements when they are created.
	 * @private
	 * @type {Object}
	 */
	this._wrapperAttributes = JSON.parse(this.getAttribute('data-wrapperAttributes'));

	var initialData = JSON.parse(this.getAttribute('data-initial'));
	this._setStatus(initialData);

	socket.subscribe(destination, this._setStatus.bind(this));
};

JStatusProto._setStatus = function(statuses) {
	if (!(statuses instanceof Array)) {
		statuses = [statuses];
	}
	this._ensureEnoughElements(statuses.length);
	for (var i = 0; i < this.childNodes.length; i++) {
		var element = this.childNodes[i];
		var status = statuses[i];
		if (status) {
			this._updateElement(element, status);
		} else if (!this._showAll) {
			// Hide the element.
			element.style.display = 'none';
		} else {
			element.innerHTML = '&nbsp;'
		}
	}
};

JStatusProto._createStatusElement = function() {
	var firstChar = this._statusElementName.charAt(0);
	var newElement;
	if (firstChar == firstChar.toUpperCase()) {
		// If the first character is uppercase, assume we want to wrap a JavaScript object.
		newElement = new window[this._statusElementName];
	} else {
		// Otherwise, we want to wrap a normal element.
		newElement = document.createElement(this._statusElementName);
	}
	if (this._wrapperAttributes) {
		Object.keys(this._wrapperAttributes).forEach(key => {
			var attribute = this._wrapperAttributes[key];
			if (attribute instanceof Object) {
				attribute = JSON.stringify(attribute);
			}
			newElement.setAttribute(key, attribute);
		});
	}
	this.appendChild(newElement);
};

JStatusProto._ensureEnoughElements = function(number) {
	var elementsNeeded = number - this.childNodes.length;
	if (elementsNeeded > 0) {
		for (var i = 0; i < elementsNeeded; i++) {
			this._createStatusElement();
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