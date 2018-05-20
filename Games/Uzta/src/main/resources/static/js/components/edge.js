class JEdge extends JLine {
	constructor() {
		super();
		this.listenToClick();

		observeAttributes(this, new Map([
			['data-ends', this._updateInput.bind(this)],
			['data-colour', this._updateColour.bind(this)]
		]));
	};

	connectedCallback() {
		if (!this._alreadySetUp) {
			this.innerHTML = '&nbsp;';

			this.classList.add('edge');
		}
		this._alreadySetUp = true;
	}

	_updateInput(ends) {
		if (!ends) {
			return;
		}
		this.setAttribute('data-input', JSON.stringify(ends));
	};

	_updateColour(colour) {
		if (!colour) {
			// todo: remove current colour
			// this.classList.remove();
		} else {
			this.classList.add(colour.toLowerCase());
		}
	};
}

mixin(JButton, JEdge);

customElements.define('j-edge', JEdge, {extends: 'div'});