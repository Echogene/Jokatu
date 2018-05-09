/**
 * A JCounter is an element that listens to updates on a STOMP destination and manages a number of child elements based
 * on the number received from that destination.
 */
class JCounter extends HTMLElement {
	connectedCallback() {
		if (!this._alreadySetUp) {
			this._counterElementName = this.getAttribute('wrapperElement');
			const destination = this.getAttribute('destination');

			this._limit = this.getAttribute('limit');

			/**
			 * Attributes to set on the counter elements when they are created.
			 * @private
			 * @type {Object}
			 */
			this._defaultAttributes = JSON.parse(this.getAttribute('data-defaultAttributes'));

			const initialData = JSON.parse(this.getAttribute('data-initial'));
			this._setCount(initialData && initialData.payload);

			socket.subscribe(destination, this._setCount.bind(this));
		}
		this._alreadySetUp = true;
	};

	_setCount(count) {
		if (this._limit > 0 && count > this._limit) {
			if (!this._number) {
				// We've just gone above the limit, so use a number instead of elements.
				while (this.firstChild) {
					this.removeChild(this.firstChild);
				}
				this._number = document.createElement('span');
				this.appendChild(this._number);
				this._createCounterElement();
			}
			this._number.innerText = `${count}×`;

		} else {
			if (this._number) {
				// We've just gone below the limit, so go back to using elements.
				while (this.firstChild) {
					this.removeChild(this.firstChild);
				}
				delete this._number;
			}
			this._ensureEnoughElements(count);
			for (let i = this.childNodes.length - 1; i >= 0; i--) {
				let element = this.childNodes[i];
				if (i >= count) {
					// Remove the element.
					this.removeChild(element);
				}
			}
		}
	};

	_createCounterElement() {
		const firstChar = this._counterElementName.charAt(0);
		let newElement;
		if (firstChar == firstChar.toUpperCase()) {
			// If the first character is uppercase, assume we want to wrap a JavaScript object.
			newElement = new window[this._counterElementName];
		} else {
			// Otherwise, we want to wrap a normal element.
			newElement = document.createElement(this._counterElementName);
		}
		if (this._defaultAttributes) {
			Object.keys(this._defaultAttributes).forEach(key => {
				let attribute = this._defaultAttributes[key];
				if (attribute instanceof Object) {
					attribute = JSON.stringify(attribute);
				}
				newElement.setAttribute(key, attribute);
			});
		}
		newElement.id = `${this.id}_${this.childNodes.length}`;

		newElement.textContent = this._defaultAttributes.text;

		this.appendChild(newElement);
	};

	_ensureEnoughElements(number) {
		const elementsNeeded = number - this.childNodes.length;
		if (elementsNeeded > 0) {
			for (let i = 0; i < elementsNeeded; i++) {
				this._createCounterElement();
			}
		}
	};
}

customElements.define('j-counter', JCounter);