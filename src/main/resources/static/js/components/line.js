var JLineProto = Object.create(HTMLDivElement.prototype);

JLineProto.createdCallback = function() {
	this._thickness = 4;
	this.style.height = `${this._thickness}px`;
	this.style.backgroundColor = 'black';
	this.style.position = 'absolute';
	this.style.pointerEvents = 'none';

	observeAttributes(this, new Map([
		['data-status', this._updatePosition.bind(this)]
	]));

	this._endsObserver = new MutationObserver(this._updatePosition.bind(this));

	this.innerHTML = '&nbsp;';

	window.addEventListener("optimizedResize", this._updatePosition.bind(this), false);
};

JLineProto.setEnds = function(start, end) {
	this.setAttribute('data-status', JSON.stringify({start: start.id, end: end.id}));
};

JLineProto._updatePosition = function(status) {
	this._endsObserver.disconnect();
	if (!status) {
		return;
	}
	var startElement = document.getElementById(status.start);
	var endElement = document.getElementById(status.end);
	if (!startElement || !endElement) {
		return;
	}

	this._endsObserver.observe(startElement.parentNode, { childList: true });
	this._endsObserver.observe(endElement.parentNode, { childList: true });

	var startOffset = this._getOffset(startElement);
	var endOffset = this._getOffset(endElement);
	// bottom right
	var x1 = startOffset.left + startOffset.width / 2;
	var y1 = startOffset.top + startOffset.height / 2;
	// top right
	var x2 = endOffset.left + endOffset.width / 2;
	var y2 = endOffset.top + startOffset.height / 2;
	// distance
	var length = Math.sqrt(((x2 - x1) * (x2 - x1)) + ((y2 - y1) * (y2 - y1)));
	// center
	var cx = ((x1 + x2) / 2) - (length / 2);
	var cy = ((y1 + y2) / 2) - (this._thickness / 2);
	// angle
	var angle = Math.atan2((y1 - y2), (x1 - x2)) * (180 / Math.PI);

	this.style.left = `${cx}px`;
	this.style.top = `${cy}px`;
	this.style.width = `${length}px`;
	this.style.transform = `rotate(${angle}deg)`;
};

JLineProto._getOffset = function(element) {
	var rect = element.getBoundingClientRect();
	return {
		left: rect.left + window.pageXOffset,
		top: rect.top + window.pageYOffset,
		width: rect.width || element.offsetWidth,
		height: rect.height || element.offsetHeight
	};
};

var JLine = document.registerElement('j-line', {
	prototype: JLineProto,
	extends: 'div'
});