var JFormProto = Object.create(HTMLFormElement.prototype);

JFormProto.attachedCallback = function() {

	this.addEventListener('submit', this._onSubmit.bind(this));

	observeAttributes(this, new Map([
		['destination', this._updateDestination.bind(this)]
	]));
};

JFormProto._updateDestination = function(destination) {
	if (this._submitButton) {
		this._submitButton.setAttribute('destination', destination);
	}
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

JFormProto.getSubmitButton = function() {
	if (this._submitButton) {
		return this._submitButton;
	}
	this._submitButton = new JButton();
	// Override the method so that the input can be based on the form's content.
	this._submitButton._getInput = this._getInput.bind(this);
	this._submitButton.id = this.id + "_submit";
	this._submitButton.innerText = 'Submit';
	this._submitButton.setAttribute('destination', this.getAttribute('destination'));
	return this._submitButton;
};

var JForm = document.registerElement('j-form', {
	prototype: JFormProto,
	extends: 'form'
});