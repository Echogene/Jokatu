class JCard extends JButton {
	constructor() {
		super();

		observeAttributes(this, new Map([
			['data-card', this._updateCard.bind(this)]
		]));
	};

	_updateCard(card) {
		if (!card) {
			return;
		}
		this.setAttribute('data-input', JSON.stringify({card: card.text}));
		this.setAttribute('data-rank', card.rank);
		this.setAttribute('data-suit', card.suit);
	};
}

customElements.define('j-card', JCard, {extends: 'button'});