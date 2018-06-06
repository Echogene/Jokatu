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
}

customElements.define('j-error-popup', JError);