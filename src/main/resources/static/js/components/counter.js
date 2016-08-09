var JCounterProto = Object.create(HTMLElement.prototype);

JCounterProto.createdCallback = function() {

	this._counterElementName = this.getAttribute('wrapperElement');
	var destination = this.getAttribute('destination');

	/**
	 * Attributes to set on the counter elements when they are created.
	 * @private
	 * @type {Object}
	 */
	this._defaultAttributes = JSON.parse(this.getAttribute('data-defaultAttributes'));

	var initialData = JSON.parse(this.getAttribute('data-initial'));
	this._setCount(initialData && initialData.payload);

	socket.subscribe(destination, this._setCount.bind(this));
};

JCounterProto._setCount = function(count) {
	this._ensureEnoughElements(count);
	for (var i = 0; i < this.childNodes.length; i++) {
		var element = this.childNodes[i];
		if (i >= count) {
			// Remove the element.
			this.removeChild(element);
		}
	}
};

JCounterProto._createCounterElement = function() {
	var firstChar = this._counterElementName.charAt(0);
	var newElement;
	if (firstChar == firstChar.toUpperCase()) {
		// If the first character is uppercase, assume we want to wrap a JavaScript object.
		newElement = new window[this._counterElementName];
	} else {
		// Otherwise, we want to wrap a normal element.
		newElement = document.createElement(this._counterElementName);
	}
	if (this._defaultAttributes) {
		Object.keys(this._defaultAttributes).forEach(key => {
			var attribute = this._defaultAttributes[key];
			if (attribute instanceof Object) {
				attribute = JSON.stringify(attribute);
			}
			newElement.setAttribute(key, attribute);
		});
	}
	newElement.id = `${this.id}_${this.childNodes.length}`;

	newElement.textContent = this._defaultAttributes.text;

	this.appendChild(newElement);
};

JCounterProto._ensureEnoughElements = function(number) {
	var elementsNeeded = number - this.childNodes.length;
	if (elementsNeeded > 0) {
		for (var i = 0; i < elementsNeeded; i++) {
			this._createCounterElement();
		}
	}
};

/**
 * A JCounter is an element that listens to updates on a STOMP destination and manages a number of child elements based
 * on the number received from that destination.
 */
var JCounter = document.registerElement('j-counter', {
	prototype: JCounterProto
});