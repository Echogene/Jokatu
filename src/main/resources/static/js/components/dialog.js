var JDialogProto = Object.create(JPopup.prototype);

JDialogProto.attachedCallback = function() {
	JPopup.prototype.attachedCallback && JPopup.prototype.attachedCallback.call(this);

	this._form = new JForm();
	this._form.id = `${this.id}_form`;
	this.appendChild(this._form);

	this._buttonBar = document.createElement('div');
	this.appendChild(this._buttonBar);

	observeAttributes(this, new Map([
		['data-buttons', this._updateButtons.bind(this)],
		['dialogid', this._updateDialogId.bind(this)],
		['data-form', this._updateForm.bind(this)]
	]));
};

JDialogProto._updateButtons = function(buttons) {
	delete this._ok;
	while (this._buttonBar.firstChild) {
		this._buttonBar.removeChild(this._buttonBar.firstChild);
	}
	if (!buttons) {
		this._ok = new JButton();
		this._ok.innerText = 'OK';
		this._ok.setAttribute('destination', `/topic/input.dialog.game.${gameId}`);
		this._ok.setAttribute('data-input', JSON.stringify({
			acknowledge: true,
			dialogId: this.getAttribute('dialogid')
		}));
		this._ok.id = `${this.id}_ok`;
		this._buttonBar.appendChild(this._ok);
	} else {

	}
};

JDialogProto._updateForm = function(form) {
	while (this._form.firstChild) {
		this._form.removeChild(this._form.firstChild);
	}
	if (form && form.fields) {
		form.fields.forEach(field => {
			let fieldDiv = document.createElement('div');

			let fieldLabel = document.createElement('label');
			fieldLabel.innerText = field.label;
			fieldLabel.for = field.name;
			fieldDiv.appendChild(fieldLabel);

			let fieldInput = document.createElement('input');
			fieldInput.type = field.type;
			fieldInput.name = field.name;
			fieldInput.value = field.value;
			fieldDiv.appendChild(fieldInput);

			this._form.appendChild(fieldDiv);
		});
	}
};

JDialogProto._updateDialogId = function(dialogId) {
	if (!(dialogId instanceof String)) {
		dialogId = JSON.stringify(dialogId);
	}
	if (this._ok) {
		this._ok.setAttribute('data-input', JSON.stringify({
			acknowledge: true,
			dialogId: dialogId
		}));
	}
};

var JDialog = document.registerElement('j-dialog', {
	prototype: JDialogProto
});