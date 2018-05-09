class JButton extends HTMLButtonElement {
	constructor() {
		super();

		this.listenToClick();
	};

	listenToClick() {
		this.addEventListener('click', () => {
			this.classList.add('submitting');
			socket.send(this.getAttribute('destination'), this._getInput())
				.then(() => this.classList.remove('submitting'))
				.catch((error, headers) => {
					this.classList.add('error');
					clearTimeout(this._errorTimeout);
					this._errorTimeout = setTimeout(() => this.classList.remove('error'), 1000);

					const popup = new JError();
					popup.id = headers.get('receipt-id');
					popup.setAttribute('data-title', 'Error');
					popup.setAttribute('data-message', error.message || error);
					popup.setAttribute('data-cover', this.id);
					popup.placeInDefaultContainer();
				});
		});
	}

	/**
	 * @return {Object} the JSON object to send to the destination
	 * @protected
	 */
	_getInput() {
		return JSON.parse(this.getAttribute('data-input'));
	};
}

customElements.define('j-button', JButton, {extends: 'button'});