var JPopupProto = Object.create(HTMLElement.prototype);

JPopupProto.createdCallback = function() {

	var template = document.querySelector('#popup_template');
	var clone = document.importNode(template.content, true);

	this._titleBar = clone.querySelector('.titleBar');
	this._message = clone.querySelector('.message');

	observeAttributes(this, new Map([
		['data-title', this._updateTitle.bind(this)],
		['data-message', this._updateMessage.bind(this)],
		['data-cover', this._updatePosition.bind(this)]
	]));

	this._titleBar.addEventListener('mouseup', (e) => {
		if (e.button === 1) {
			this.hide();
		}
	});

	this.appendChild(clone);
};

JPopupProto.hide = function() {
	if (this._line) {
		this.parentNode.removeChild(this._line);
	}
	this.parentNode.removeChild(this);
};

JPopupProto.initialise = function() {
	var container = document.getElementById('popup-container');
	if (!container) {
		container = document.createElement('div');
		container.id = 'popup-container';
		container.classList.add('overlay');
		document.body.appendChild(container);
	}
	container.appendChild(this);
};

JPopupProto._updateTitle = function(title) {
	this._titleBar.innerText = title;
};

JPopupProto._updateMessage = function(message) {
	this._message.innerText = message;
};

JPopupProto._updatePosition = function(coveeId) {
	var covee = document.getElementById(coveeId);
	if (covee) {
		if (!this._line) {
			this._line = new JLine();
			this.parentNode.appendChild(this._line);
		}
		this._line.setEnds(this, covee);
	} else if (this._line) {
		// The covee has been removed, so hide this.
		this.hide();
	}
};

var JPopup = document.registerElement('j-popup', {
	prototype: JPopupProto
});