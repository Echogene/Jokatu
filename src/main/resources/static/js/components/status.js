var JStatusProto = Object.create(HTMLElement.prototype);

JStatusProto.createdCallback = function() {

	this._statusElementName = this.getAttribute('wrapperElement');
	this._showAll = this.getAttribute('showAll');
	this._removeOldChildren = this.getAttribute('removeOldChildren');
	var destination = this.getAttribute('destination');

	/**
	 * Attributes to set on the status elements when they are created.
	 * @private
	 * @type {Object}
	 */
	this._wrapperAttributes = JSON.parse(this.getAttribute('data-wrapperAttributes'));

	var initialData = JSON.parse(this.getAttribute('data-initial'));
	this._setStatus(initialData && initialData.payload);

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
		} else if (this._showAll) {
			element.innerHTML = '&nbsp;'
		} else if (this._removeOldChildren) {
			// Remove the element.
			this.removeChild(element);
		} else {
			// Hide the element.
			element.style.display = 'none';
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
	newElement.id = `${this.id}_${this.childNodes.length}`;
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

/**
 * A JStatus is an element that listens to updates on a STOMP destination and manages its child elements with the data
 * received from that destination.  If the data returned is an array, it spawns/hides children per array element.  If
 * the data is not an array, it is treated as a singleton array.
 *
 * When it has spawned/hidden the appropriate children, it passes the corresponding array element into each child's
 * <code>data-status</code> attribute and updates the child's text content if the array element is a string or is an
 * object with a <code>text</code> string field.
 */
var JStatus = document.registerElement('j-status', {
	prototype: JStatusProto
});