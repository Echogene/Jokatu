var JPlayerProto = Object.create(HTMLElement.prototype);

JPlayerProto.createdCallback = function() {

	var template = document.querySelector('#player_template');
	this._content = document.importNode(template.content, true);

	this._name = this._content.querySelector('.name');
	this._status = this._content.querySelector('.status');

	observeAttributes(this, new Map([
		['data-player', this._updateStatus.bind(this)]
	]));

	this.appendChild(this._content);
};

JPlayerProto._updateStatus = function(status) {
	if (!status) {
		return;
	}
	this._name.innerText = status.name;
	if (status.online) {
		this.classList.add('online');
	} else {
		this.classList.remove('online')
	}
};

var JPlayer = document.registerElement('j-player', {
	prototype: JPlayerProto
});