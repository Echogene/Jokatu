var JNodeProto = Object.create(HTMLElement.prototype);

JNodeProto.attachedCallback = function() {

	var template = document.querySelector('#node-template');
	var clone = document.importNode(template.content, true);

	this._background = clone.querySelector('.background');
	this._valuesContainer = clone.querySelector('.values');

	this.appendChild(clone);

	observeAttributes(this, new Map([
		['data-node', this._updateNode.bind(this)]
	]));
};

JNodeProto._updateNode = function(node) {
	if (!node) {
		return;
	}
	this._ensureEnoughElements(node.values.length);
	
	for (var i = this._valuesContainer.childNodes.length - 1; i >= 0; i--) {
		var element = this._valuesContainer.childNodes[i];
		var value = node.values[i];
		if (value) {
			this._updateElement(element, value);
		} else {
			// Remove the element.
			this._valuesContainer.removeChild(element);
		}
	}
};

JNodeProto._ensureEnoughElements = function(number) {
	var elementsNeeded = number - this._valuesContainer.childNodes.length;
	if (elementsNeeded > 0) {
		for (var i = 0; i < elementsNeeded; i++) {
			this._valuesContainer.appendChild(document.createElement('span'));
		}
	}
};

JNodeProto._updateElement = function(element, value) {
	element.innerText = value;
};

/**
 * A JGraph is an element that listens to updates on a STOMP destination and manages nodes and edges on a graph
 */
var JNode = document.registerElement('j-node', {
	prototype: JNodeProto
});