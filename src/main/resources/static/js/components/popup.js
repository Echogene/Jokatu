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
	var container = document.getElementById('popup_container');
	if (!container) {
		container = document.createElement('div');
		container.id = 'popup_container';
		document.body.appendChild(container);
	}
	container.appendChild(this);
};

JPopupProto._updateMessage = function() {
	this._titleBar.innerText = this.getAttribute('data-title');
};

JPopupProto._updateTitle = function() {
	this._message.innerText = this.getAttribute('data-message');
};

JPopupProto._updatePosition = function() {
	var covee = document.getElementById(this.getAttribute('data-cover'));
	if (covee) {
		if (!this._line) {
			this._line = new JLine();
			this.parentNode.appendChild(this._line);
		}
		this._line.setEnds(this, covee);
	}
};

var JPopup = document.registerElement('j-popup', {
	prototype: JPopupProto
});