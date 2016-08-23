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

	if (!form) {
		return;
	}

	if (form.fields) {
		form.fields.forEach(field => {
			let fieldDiv = document.createElement('div');

			let fieldLabel = document.createElement('label');
			fieldLabel.innerText = field.label;
			fieldLabel.for = field.name;
			fieldDiv.appendChild(fieldLabel);

			if (field.type == 'select') {
				let select = document.createElement('select');
				select.name = field.name;
				field.options.forEach(o => {
					let option = document.createElement('option');
					option.value = o.name;
					option.innerText = o.label;
					if (o.selected) {
						option.selected = true;
					}
					select.appendChild(option);
				});
				fieldDiv.appendChild(select);
			} else {
				let fieldInput = document.createElement('input');
				fieldInput.type = field.type;
				fieldInput.name = field.name;
				fieldInput.value = field.value;
				fieldDiv.appendChild(fieldInput);
			}

			this._form.insertBefore(fieldDiv, this._form.lastChild);
		});
	}

	this._dialogIdField = document.createElement('input');
	this._dialogIdField.type = 'hidden';
	this._dialogIdField.name = 'dialogId';
	this._dialogIdField.value = this.getAttribute('dialogid');
	this._form.insertBefore(this._dialogIdField, this._form.lastChild);
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