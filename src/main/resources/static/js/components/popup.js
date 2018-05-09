class JPopup extends HTMLElement {
	connectedCallback() {
		if (!this._alreadySetUp) {
			const template = document.querySelector('#popup_template');
			const clone = document.importNode(template.content, true);

			this._titleBar = clone.querySelector('.titleBar');
			this._message = clone.querySelector('.message');

			this.classList.add('popup');

			observeAttributes(this, new Map([
				['data-title', this._updateTitle.bind(this)],
				['data-message', this._updateMessage.bind(this)],
				['data-cover', this._updatePosition.bind(this)]
			]));

			this.appendChild(clone);
		}
		this._alreadySetUp = true;
	}

	hide() {
		if (this._line) {
			this.parentNode.removeChild(this._line);
		}
		this.parentNode.removeChild(this);
	};

	_updateTitle(title) {
		this._titleBar.innerText = title;
	};

	_updateMessage(message) {
		this._message.innerText = message;
	};

	_updatePosition(coveeId) {
		const covee = document.getElementById(coveeId);
		if (covee) {
			if (!this._line) {
				this._line = new JLine();
				this.parentNode.appendChild(this._line);
			}
			this._line.setEnds(this, covee);
		} else if (this._line) {
			// The covee has been removed, so hide this.
			this.hide();
		}
	};
}

customElements.define('j-popup', JPopup);