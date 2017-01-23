var JIntegerProto = Object.create(HTMLElement.prototype);

JIntegerProto.attachedCallback = function() {
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

	this.addEventListener('wheel', this._onInputWheel.bind(this));

	observeAttributes(this, new Map([
		['name', this._updateName.bind(this)],
		['value', this._updateValue.bind(this)],
		['postivetext', this._updatePositiveText.bind(this)],
		['negativetext', this._updateNegativeText.bind(this)]
	]));

	this.appendChild(clone);
};

JIntegerProto.focus = function() {
	this._input.focus();
};

JIntegerProto._onClick = function(direction) {
	this._value = this._value + direction;
	this._update();
};

JIntegerProto._onInvert = function() {
	this._value = -this._value;
	this._update();
};

JIntegerProto._updatePositiveText = function(signText = '') {
	this._signText.innerText = this.getAttribute('value') < 0 ? this.getAttribute('negativetext') : signText;
};

JIntegerProto._updateNegativeText = function(signText = '−') {
	this._signText.innerText = this.getAttribute('value') < 0 ? signText : this.getAttribute('positivetext');
};

JIntegerProto._updateName = function(name) {
	this._valueHolder.name = name;
};

JIntegerProto._update = function() {
	this._valueHolder.value = this._value;
	this._input.value = Math.abs(this._value);
	this._signText.innerText = this._value < 0 ? this.getAttribute('negativetext') : this.getAttribute('positivetext');
};

JIntegerProto._updateValue = function(value) {
	this._value = value;
	this._update();
};

JIntegerProto._onInputChange = function() {
	const originalInputTextValue = this._input.value;
	const originalInputNumberValue = parseInt(originalInputTextValue);
	if (!isNaN(originalInputNumberValue)) {
		this._value = Math.sign(this._value) * originalInputNumberValue;
		this._update();
	}
};

JIntegerProto._onInputWheel = function(event) {
	const δ = event.deltaY;
	if (δ < 0) {
		this._onClick(1);

	} else if (δ > 0) {
		this._onClick(-1);
	}
};

var JInteger = document.registerElement('j-integer', {
	prototype: JIntegerProto
});