class JMessageBox extends HTMLElement {
	connectedCallback() {
		if (!this._alreadySetUp) {
			/**
			 * @type {string}
			 * @private
			 */
			this._messageElementName = this.getAttribute('wrapperElement');
			const destination = this.getAttribute('destination');

			const initialData = JSON.parse(this.getAttribute('data-initial')) || [];
			initialData.forEach(datum => this._createMessage(datum.payload, datum.headers));

			socket.subscribe(destination, this._createMessage.bind(this));
		}
		this._alreadySetUp = true;
	}

	_createMessage(body, headers) {
		// todo: use elements.js createElement
		const firstChar = this._messageElementName.charAt(0);
		let element;
		if (firstChar == firstChar.toUpperCase()) {
			// If the first character is uppercase, assume we want to wrap a JavaScript object.
			element = new window[this._messageElementName];
		} else {
			// Otherwise, we want to wrap a normal element.
			element = document.createElement(this._messageElementName);
		}
		element.className = 'message';
		if (typeof body === 'string') {
			element.textContent = body;
		} else if (body && typeof body === 'object' && typeof body.text === 'string') {
			element.textContent = body.text;
		}
		var attributeName = this.getAttribute('attributeName');
		if (attributeName) {
			element.setAttribute(attributeName, JSON.stringify({payload: body, headers: headers}));
		}
		if (this.getAttribute('toppost')) {
			this.insertBefore(element, this.firstChild)
		} else {
			this.appendChild(element);
		}
	};
}

customElements.define('j-message-box', JMessageBox);