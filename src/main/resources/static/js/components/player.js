class JPlayer extends HTMLElement {
	constructor() {
		super();
	};

	connectedCallback() {
		if (!this._alreadySetUp) {
			const template = document.querySelector('#player_template');
			this._content = document.importNode(template.content, true);

			this._name = this._content.querySelector('.name');
			this._status = this._content.querySelector('.status');

			this.appendChild(this._content);

			observeAttributes(this, new Map([
				['data-player', this._updateStatus.bind(this)]
			]));
		}
		this._alreadySetUp = true;
	}

	/**
	 * @param player
	 * @param {string} player.name
	 * @param {boolean} player.isOnline
	 * @private
	 */
	_updateStatus(player) {
		if (!player) {
			return;
		}
		this._name.innerText = player.name;
		if (player.isOnline) {
			this.classList.add('online');
			this.title = `${player.name} is online`;
		} else {
			this.classList.remove('online');
			this.title = `${player.name} is offline`;
		}
	};
}

customElements.define('j-player', JPlayer);