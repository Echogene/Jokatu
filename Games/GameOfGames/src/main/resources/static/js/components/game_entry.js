class JGameEntry extends HTMLElement {
	constructor() {
		super();

		observeAttributes(this, new Map([
			['data-game', this._updateStatus.bind(this)]
		]));
	};

	connectedCallback() {
		if (!this._alreadySetUp) {
			const template = document.querySelector('#game_entry_template');
			this._content = document.importNode(template.content, true);

			this._id = this._content.querySelector('.id');
			this._name = this._content.querySelector('.name');

			this.appendChild(this._content);
		}
		this._alreadySetUp = true;
	}

	_updateStatus(status) {
		if (!status) {
			return;
		}
		this._id.innerText = status.gameId;
		this._name.innerText = status.gameName;
		this._name.href = `/game/${status.gameId}`
	}
}

customElements.define('j-game-entry', JGameEntry);