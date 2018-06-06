/**
 * An element that maintains an enumerated list of buttons depending on how many options it has.
 *
 * todo: can this be replaced by j-status?  It listens to an attribute to change its children rather than a websocket
 * todo: destination
 */
class JOptions extends HTMLElement {
	connectedCallback() {
		if (!this._alreadySetUp) {
			observeAttributes(this, new Map([
				['data-options', this._updateOptions.bind(this)]
			]));
		}
		this._alreadySetUp = true;
	}

	/**
	 * @param {Array.<{input: string|Object, display: string}>} options
	 * @private
	 */
	_updateOptions(options) {
		if (!options) {
			return;
		}
		this._ensureEnoughElements(options.length);
		for (let i = 0; i < this.childNodes.length; i++) {
			const button = this.childNodes[i];
			const option = options[i];
			if (option) {
				this._updateElement(button, option);
			} else {
				// Remove the button.
				this.removeChild(button);
			}
		}
	};

	_updateElement(button, option) {
		button.textContent = option.display;
		button.setAttribute('data-input', JSON.stringify(option.input));
		button.setAttribute('destination', this.getAttribute('destination'));
	};

	_ensureEnoughElements(number) {
		const elementsNeeded = number - this.childNodes.length;
		if (elementsNeeded > 0) {
			for (let i = 0; i < elementsNeeded; i++) {
				this._createOptionButton();
			}
		}
	};

	_createOptionButton() {
		const newButton = new JButton();
		newButton.id = `${this.id}_${this.childNodes.length}`;
		this.appendChild(newButton);
	};
}

customElements.define('j-options', JOptions);