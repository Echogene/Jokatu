var JEdgeProto = Object.create(JLine.prototype);

JEdgeProto.attachedCallback = function() {
	JLine.prototype.attachedCallback.call(this);
};

var JEdge = document.registerElement('j-edge', {
	prototype: JEdgeProto,
	extends: 'div'
});