var JOptionsProto = Object.create(HTMLElement.prototype);

JOptionsProto.createdCallback = function() {

	observeAttributes(this, new Map([
		['data-options', this._updateOptions.bind(this)]
	]));
};

/**
 * @param {Array.<{input: string|Object, display: string}>} options
 * @private
 */
JOptionsProto._updateOptions = function(options) {
	if (!options) {
		return;
	}
	this._ensureEnoughElements(options.length);
	for (var i = 0; i < this.childNodes.length; i++) {
		var button = this.childNodes[i];
		var option = options[i];
		if (option) {
			this._updateElement(button, option);
		} else {
			// Remove the button.
			this.removeChild(button);
		}
	}
};

JOptionsProto._updateElement = function(button, option) {
	button.textContent = option.display;
	button.setAttribute('data-input', JSON.stringify(option.input));
	button.setAttribute('destination', this.getAttribute('destination'));
};

JOptionsProto._ensureEnoughElements = function(number) {
	var elementsNeeded = number - this.childNodes.length;
	if (elementsNeeded > 0) {
		for (var i = 0; i < elementsNeeded; i++) {
			this._createOptionButton();
		}
	}
};

JOptionsProto._createOptionButton = function() {
	var newButton = new JButton();
	newButton.id = `${this.id}_${this.childNodes.length}`;
	this.appendChild(newButton);
};

var JOptions = document.registerElement('j-options', {
	prototype: JOptionsProto
});