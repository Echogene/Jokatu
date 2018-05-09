class JPlayer extends HTMLElement {
	constructor() {
		super();

		observeAttributes(this, new Map([
			['data-player', this._updateStatus.bind(this)]
		]));
	};

	connectedCallback() {
		if (!this._alreadySetUp) {
			const template = document.querySelector('#player_template');
			this._content = document.importNode(template.content, true);

			this._name = this._content.querySelector('.name');
			this._status = this._content.querySelector('.status');

			this.appendChild(this._content);
		}
		this._alreadySetUp = true;
	}

	_updateStatus(status) {
		if (!status) {
			return;
		}
		this._name.innerText = status.name;
		if (status.online) {
			this.classList.add('online');
			this.title = `${status.name} is online`;
		} else {
			this.classList.remove('online');
			this.title = `${status.name} is offline`;
		}
	};
}

customElements.define('j-player', JPlayer);