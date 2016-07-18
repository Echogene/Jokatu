var JEdgeProto = Object.create(JLine.prototype);
mixin(JEdgeProto, JButton.prototype);

JEdgeProto.attachedCallback = function() {
	JLine.prototype.attachedCallback && JLine.prototype.attachedCallback.call(this);
	JButton.prototype.attachedCallback && JButton.prototype.attachedCallback.call(this);

	observeAttributes(this, new Map([
		['data-ends', this._updateInput.bind(this)],
		['data-colour', this._updateColour.bind(this)]
	]));
};

JEdgeProto._updateInput = function(ends) {
	if (!ends) {
		return;
	}
	this.setAttribute('data-input', JSON.stringify(ends));
};

JEdgeProto._updateColour = function(colour) {
	if (!colour) {
		// todo: remove current colour
		// this.classList.remove();
	} else {
		this.classList.add(colour.toLowerCase());
	}
};

var JEdge = document.registerElement('j-edge', {
	prototype: JEdgeProto,
	extends: 'div'
});