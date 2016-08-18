var JFormProto = Object.create(HTMLFormElement.prototype);

JFormProto.attachedCallback = function() {

	var template = document.querySelector('#form_template');
	var clone = document.importNode(template.content, true);

	this._submitButton = clone.querySelector('button');
	// Override the method so that the input can be based on the form's content.
	this._submitButton._getInput = this._getInput.bind(this);
	this._submitButton.id = this.id + "_submit";

	this.addEventListener('submit', this._onSubmit.bind(this));

	this.appendChild(clone);

	observeAttributes(this, new Map([
		['destination', this._updateDestination.bind(this)]
	]));
};

JFormProto._updateDestination = function(destination) {
	this._submitButton.setAttribute('destination', destination);
};

JFormProto._getInput = function() {
	var object = {};
	for (var i = 0; i < this.elements.length; i++) {
		var element = this.elements[i];
		if (element.value) {
			object[element.name] = element.value;
		}
	}
	return object;
};

JFormProto._onSubmit = function(e) {
	e.preventDefault();
	return false;
};

var JForm = document.registerElement('j-form', {
	prototype: JFormProto,
	extends: 'form'
});