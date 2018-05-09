class JError extends JPopup {
	connectedCallback() {
		if (!this._alreadySetUp) {
			super.connectedCallback();

			this._titleBar.addEventListener('mouseup', (e) => {
				if (e.button === 1) {
					this.hide();
				}
			});
		}
		this._alreadySetUp = true;
	}

	placeInDefaultContainer() {
		let container = document.getElementById('popup-container');
		if (!container) {
			container = document.createElement('div');
			container.id = 'popup-container';
			container.classList.add('overlay');
			document.body.appendChild(container);
		}
		container.appendChild(this);
	};
}

customElements.define('j-error-popup', JError);