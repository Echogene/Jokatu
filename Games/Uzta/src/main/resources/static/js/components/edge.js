var JEdgeProto = Object.create(JLine.prototype);
mixin(JEdgeProto, JButton.prototype);

JEdgeProto.attachedCallback = function() {
	JLine.prototype.attachedCallback && JLine.prototype.attachedCallback.call(this);
	JButton.prototype.attachedCallback && JButton.prototype.attachedCallback.call(this);

	observeAttributes(this, new Map([
		['data-ends', this._updateInput.bind(this)]
	]));
};

JEdgeProto._updateInput = function(ends) {
	if (!ends) {
		return;
	}
	this.setAttribute('data-input', JSON.stringify(ends));
};

var JEdge = document.registerElement('j-edge', {
	prototype: JEdgeProto,
	extends: 'div'
});