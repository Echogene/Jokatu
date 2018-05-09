class JForm extends HTMLFormElement {
	constructor() {
		super();

		this.addEventListener('submit', JForm._onSubmit.bind(this));

		observeAttributes(this, new Map([
			['destination', this._updateDestination.bind(this)],
			['submit-text', this._updateSubmitText.bind(this)]
		]));
	};

	_updateDestination(destination) {
		if (this._submitButton) {
			this._submitButton.setAttribute('destination', destination);
		}
	};

	_updateSubmitText(submitText) {
		if (this._submitButton) {
			this._submitButton.innerText = submitText || 'Submit';
		}
	};

	_getInput() {
		const object = {};
		for (let i = 0; i < this.elements.length; i++) {
			const element = this.elements[i];
			if (
				typeof element.value !== 'undefined'
				&& typeof element.name === 'string'
				&& element.value !== ''
				&& element.name !== ''
			) {
				object[element.name] = JForm._getFieldValue(element);
			}
		}
		return object;
	};

	static _getFieldValue(fieldElement) {
		if (fieldElement.value === 'true') {
			return true;
		} else if (fieldElement.value === 'false') {
			return false;
		} else if (
			fieldElement.type === 'number'
				|| fieldElement.inputmode === 'numeric'
				|| fieldElement.getAttribute('inputmode') === 'numeric'
		) {
			return parseInt(fieldElement.value);
		} else {
			return fieldElement.value;
		}
	};

	static _onSubmit(e) {
		e.preventDefault();
		return false;
	};

	getSubmitButton() {
		if (this._submitButton) {
			return this._submitButton;
		}
		this._submitButton = new JButton();
		// Override the method so that the input can be based on the form's content.
		this._submitButton._getInput = this._getInput.bind(this);
		this._submitButton.id = this.id + "_submit";
		this._submitButton.innerText = this.getAttribute('submit-text') || 'Submit';
		this._submitButton.setAttribute('destination', this.getAttribute('destination'));
		return this._submitButton;
	};
}

customElements.define('j-form', JForm, {extends: 'form'});