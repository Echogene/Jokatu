var JDialogProto = Object.create(JPopup.prototype);

JDialogProto.attachedCallback = function() {
	JPopup.prototype.attachedCallback && JPopup.prototype.attachedCallback.call(this);

	this._form = new JForm();
	this._form.id = `${this.id}_form`;
	this._form.setAttribute('destination', `/topic/input.dialog.game.${gameId}`);
	this.appendChild(this._form);

	this._buttonBar = document.createElement('div');
	this._buttonBar.classList.add('buttons');
	this._form.appendChild(this._buttonBar);

	observeAttributes(this, new Map([
		['data-buttons', this._updateButtons.bind(this)],
		['dialogid', this._updateDialogId.bind(this)],
		['data-form', this._updateForm.bind(this)]
	]));
};

JDialogProto._updateButtons = function(buttons) {
	while (this._buttonBar.firstChild) {
		this._buttonBar.removeChild(this._buttonBar.firstChild);
	}
	if (!buttons) {
		this._buttonBar.appendChild(this._form.getSubmitButton());
	} else {

	}
};

JDialogProto._updateForm = function(form) {
	for (let i = this._form.childNodes.length - 1; i >= 0; i--) {
		let element = this._form.childNodes[i];
		if (!element.classList.contains('buttons')) {
			this._form.removeChild(element);
		}
	}
	this._form.setAttribute('submit-text', 'Submit');

	if (form && form.fields) {
		let focussed = false;
		for (var i = 0; i < form.fields.length; i++) {
			var field = form.fields[i];

			let fieldDiv = document.createElement('div');

			var formElement = this._addFormElement(fieldDiv, field);

			this._form.insertBefore(fieldDiv, this._form.lastChild);
			if (!focussed && formElement) {
				formElement.focus();
				focussed = true;
			}
		}
	} else {
		let acknowledge = document.createElement('input');
		acknowledge.type = 'hidden';
		acknowledge.name = 'acknowledge';
		acknowledge.value = false;
		this._form.insertBefore(acknowledge, this._form.lastChild);

		this._form.setAttribute('submit-text', 'Cancel');
	}

	this._dialogIdField = document.createElement('input');
	this._dialogIdField.type = 'hidden';
	this._dialogIdField.name = 'dialogId';
	this._dialogIdField.value = this.getAttribute('dialogid');
	this._form.insertBefore(this._dialogIdField, this._form.lastChild);
};

JDialogProto._addFormElement = function(fieldDiv, field) {

	if (!field.type) {
		fieldDiv.innerText = field.label;
		fieldDiv.classList.add('header');
		return;
	}

	let fieldLabel = document.createElement('label');
	fieldLabel.innerText = field.label;
	fieldLabel.for = field.name;
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
	fieldDiv.appendChild(fieldElement);

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