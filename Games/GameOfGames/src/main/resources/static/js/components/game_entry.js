var JGameEntryProto = Object.create(HTMLElement.prototype);

JGameEntryProto.createdCallback = function() {

	var template = document.querySelector('#game_entry_template');
	this._content = document.importNode(template.content, true);

	this._id = this._content.querySelector('.id');
	this._name = this._content.querySelector('.name');

	observeAttributes(this, new Map([
		['data-game', this._updateStatus.bind(this)]
	]));

	this.appendChild(this._content);
};

JGameEntryProto._updateStatus = function(status) {
	if (!status) {
		return;
	}
	this._id.innerText = status.gameId;
	this._name.innerText = status.gameName;
	this._name.href = `/game/${status.gameId}`
};

var JGameEntry = document.registerElement('j-game-entry', {
	prototype: JGameEntryProto
});