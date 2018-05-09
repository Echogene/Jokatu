class JInteger extends HTMLElement {
	constructor() {
		super();

		this.addEventListener('wheel', this._onInputWheel.bind(this));

		observeAttributes(this, new Map([
			['name', this._updateName.bind(this)],
			['value', this._updateValue.bind(this)],
			['positiveText', this._updatePositiveText.bind(this)],
			['negativeText', this._updateNegativeText.bind(this)]
		]));
	};

	connectedCallback() {
		if (!this._alreadySetUp) {
			const template = document.querySelector('#integer_template');
			const clone = document.importNode(template.content, true);

			this._signText = clone.querySelector('.signText');
			this._signText.addEventListener('click', this._onInvert.bind(this));

			this._input = clone.querySelector('.numericInput');
			this._input.addEventListener('change', this._onInputChange.bind(this));

			this._valueHolder = clone.querySelector('.valueHolder');

			this._up = clone.querySelector('.up');
			this._up.addEventListener('click', this._onClick.bind(this, 1));

			this._down = clone.querySelector('.down');
			this._down.addEventListener('click', this._onClick.bind(this, -1));

			this.appendChild(clone);
		}
		this._alreadySetUp = true;
	}

	focus() {
		this._input.focus();
	};

	_onClick(direction) {
		this._value = this._value + direction;
		this._update();
	};

	_onInvert() {
		this._value = -this._value;
		this._update();
	};

	_updatePositiveText(signText = '') {
		this._signText.innerText = this.getAttribute('value') < 0 ? this.getAttribute('negativeText') : signText;
	};

	_updateNegativeText(signText = '−') {
		this._signText.innerText = this.getAttribute('value') < 0 ? signText : this.getAttribute('positiveText');
	};

	_updateName(name) {
		this._valueHolder.name = name;
	};

	_update() {
		this._valueHolder.value = this._value;
		this._input.value = Math.abs(this._value);
		this._signText.innerText = this._value < 0 ? this.getAttribute('negativeText') : this.getAttribute('positiveText');
	};

	_updateValue(value) {
		this._value = value;
		this._update();
	};

	_onInputChange() {
		const originalInputTextValue = this._input.value;
		const originalInputNumberValue = parseInt(originalInputTextValue);
		if (!isNaN(originalInputNumberValue)) {
			this._value = Math.sign(this._value) * originalInputNumberValue;
			this._update();
		}
	};

	_onInputWheel(event) {
		const δ = event.deltaY;
		if (δ < 0) {
			this._onClick(1);

		} else if (δ > 0) {
			this._onClick(-1);
		}
	};
}

customElements.define('j-integer', JInteger);