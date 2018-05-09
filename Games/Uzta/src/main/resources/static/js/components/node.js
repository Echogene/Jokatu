class JNode extends HTMLElement {
	constructor() {
		super();

		observeAttributes(this, new Map([
			['data-node', this._updateNode.bind(this)]
		]));
	};

	connectedCallback() {
		if (!this._alreadySetUp) {
			const template = document.querySelector('#node-template');
			const clone = document.importNode(template.content, true);

			this._background = clone.querySelector('.background');
			this._valuesContainer = clone.querySelector('.values');

			this.appendChild(clone);
		}
		this._alreadySetUp = true;
	}

	_updateNode(node) {
		if (!node) {
			return;
		}
		this._ensureEnoughElements(node.values.length);

		for (let i = this._valuesContainer.childNodes.length - 1; i >= 0; i--) {
			const element = this._valuesContainer.childNodes[i];
			const value = node.values[i];
			if (value) {
				JNode._updateElement(element, value);
			} else {
				// Remove the element.
				this._valuesContainer.removeChild(element);
			}
		}
	};

	_ensureEnoughElements(number) {
		const elementsNeeded = number - this._valuesContainer.childNodes.length;
		if (elementsNeeded > 0) {
			for (let i = 0; i < elementsNeeded; i++) {
				this._valuesContainer.appendChild(document.createElement('span'));
			}
		}
	};

	static _updateElement(element, value) {
		element.innerText = value;
	};
}

customElements.define('j-node', JNode);