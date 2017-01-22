const JDialogProto = Object.create(JPopup.prototype);

JDialogProto.attachedCallback = function() {
	JPopup.prototype.attachedCallback && JPopup.prototype.attachedCallback.call(this);

	this._form = new JForm();
	this._form.id = `${this.id}_form`;
	this._form.setAttribute('destination', `/topic/input.dialog.game.${gameId}`);
	this.appendChild(this._form);

	this._buttonBar = document.createElement('div');
	this._buttonBar.classList.add('buttons');
	this._form.appendChild(this._buttonBar);

	this._buttonBar.appendChild(this._form.getSubmitButton());

	observeAttributes(this, new Map([
		['cancellable', this._updateCancellable.bind(this)],
		['dialogid', this._updateDialogId.bind(this)],
		['data-form', this._updateForm.bind(this)]
	]));
};

JDialogProto._updateCancellable = function(cancellable) {
	if (cancellable) {
		if (!this._cancelButton) {
			this._cancelButton = new JButton();
			this._cancelButton.id = this.id + "_submit";
			this._cancelButton.innerText = 'Cancel';
			this._cancelButton.setAttribute('destination', this._form.getAttribute('destination'));
			// Override the method so that the input can be based on current dialog ID.
			this._cancelButton._getInput = () => ({
				dialogId: this.getAttribute('dialogid'),
				acknowledge: false
			});

			this._buttonBar.appendChild(this._cancelButton);
		}
	} else if (this._cancelButton) {
		this._buttonBar.removeChild(this._cancelButton);
		delete this._cancelButton;
	}
};

JDialogProto._updateForm = function(form) {
	for (let i = this._form.childNodes.length - 1; i >= 0; i--) {
		const element = this._form.childNodes[i];
		if (!element.classList.contains('buttons')) {
			this._form.removeChild(element);
		}
	}
	this._form.setAttribute('submit-text', 'Submit');

	if (form && form.fields) {
		let focussed = false;
		for (let i = 0; i < form.fields.length; i++) {
			const field = form.fields[i];

			const fieldDiv = document.createElement('div');

			const formElement = this._addFormElement(fieldDiv, field);

			this._form.insertBefore(fieldDiv, this._form.lastChild);
			if (!focussed && formElement) {
				formElement.focus();
				focussed = true;
			}
		}
	} else {
		const acknowledge = document.createElement('input');
		acknowledge.type = 'hidden';
		acknowledge.name = 'acknowledge';
		acknowledge.value = true;
		this._form.insertBefore(acknowledge, this._form.lastChild);

		this._form.setAttribute('submit-text', 'Ok');
	}

	this._dialogIdField = document.createElement('input');
	this._dialogIdField.type = 'hidden';
	this._dialogIdField.name = 'dialogId';
	this._dialogIdField.value = this.getAttribute('dialogid');
	this._form.insertBefore(this._dialogIdField, this._form.lastChild);
};

/**
 * @param {HTMLDivElement} fieldDiv
 * @param {{
 *     type: 'text' | 'checkbox' | 'range' | 'number' | 'select',
 *     name: string
 *     value: string
 *     label: string
 *     options: {
 *         name: string,
 *         label: string,
 *         selected: boolean
 *     }[]
 * }} field
 * @returns {HTMLElement | undefined}
 * @private
 */
JDialogProto._addFormElement = function(fieldDiv, field) {

	if (!field.type) {
		fieldDiv.innerText = field.label;
		fieldDiv.classList.add('header');
		return;
	}

	const fieldLabel = document.createElement('label');
	fieldLabel.innerText = field.label;
	fieldDiv.appendChild(fieldLabel);

	let fieldElement;
	if (field.type == 'select') {
		fieldElement = document.createElement('select');
		fieldElement.name = field.name;
		field.options.forEach(o => {
			let option = document.createElement('option');
			option.value = o.name;
			option.innerText = o.label;
			if (o.selected) {
				option.selected = true;
			}
			fieldElement.appendChild(option);
		});
		fieldDiv.appendChild(fieldElement);
	} else {
		fieldElement = document.createElement('input');
		fieldElement.type = field.type;
		fieldElement.name = field.name;
		fieldElement.value = field.value;
	}
	fieldLabel.appendChild(fieldElement);

	return fieldElement;
};

JDialogProto._updateDialogId = function(dialogId) {
	if (!(dialogId instanceof String)) {
		dialogId = JSON.stringify(dialogId);
	}
	if (this._dialogIdField) {
		this._dialogIdField.value = dialogId;
	}
};

var JDialog = document.registerElement('j-dialog', {
	prototype: JDialogProto
});