var JCardProto = Object.create(JButton.prototype);

JCardProto.createdCallback = function() {
	JButton.prototype.createdCallback.call(this);

	observeAttributes(this, new Map([
		['data-card', this._updateCard.bind(this)]
	]));
};

JCardProto._updateCard = function(card) {
	if (!card) {
		return;
	}
	this.setAttribute('data-input', JSON.stringify({card: card.text}));
	this.setAttribute('data-rank', card.rank);
	this.setAttribute('data-suit', card.suit);
};

var JCard = document.registerElement('j-card', {
	prototype: JCardProto,
	extends: 'button'
});