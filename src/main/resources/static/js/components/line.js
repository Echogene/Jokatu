var JLineProto = Object.create(HTMLDivElement.prototype);

JLineProto.attachedCallback = function() {
	this._thickness = 4;
	this.style.height = `${this._thickness}px`;
	this.style.backgroundColor = 'black';
	this.style.position = 'absolute';
	this.style.pointerEvents = 'none';

	this._endsObserver = new MutationObserver(this._updatePositionFromEnds.bind(this));

	observeAttributes(this, new Map([
		['data-ends', this._updatePosition.bind(this)]
	]));

	this.innerHTML = '&nbsp;';

	window.addEventListener("optimizedResize", this._updatePositionFromEnds.bind(this), false);
};

JLineProto.setEnds = function(start, end) {
	this.setAttribute('data-ends', JSON.stringify({start: start.id, end: end.id}));
};

JLineProto._updatePositionFromEnds = function() {
	this._updatePosition(JSON.parse(this.getAttribute('data-ends')));
};

JLineProto._updatePosition = function(ends, attempt = 0) {
	var startElement = ends && ends.start && document.getElementById(ends.start);
	var endElement = ends && ends.end && document.getElementById(ends.end);

	var endElementsChanged = (startElement != this._startElement) || (endElement != this._endElement);
	if (endElementsChanged) {
		this._endsObserver.disconnect();
	}

	this._startElement = startElement;
	this._endElement = endElement;
	if (!startElement || !endElement) {
		return;
	}

	if (endElementsChanged) {
		this._endsObserver.observe(startElement, { attributes: true, attributeFilter: ['style'] });
		this._endsObserver.observe(endElement, { attributes: true, attributeFilter: ['style'] });

		this._endsObserver.observe(startElement.parentNode, { childList: true });
		if (endElement.parentNode != startElement.parentNode) {
			this._endsObserver.observe(endElement.parentNode, { childList: true });
		}
	}

	var startOffset = this._getOffset(startElement);
	var endOffset = this._getOffset(endElement);
	// bottom right
	var x1 = startOffset.left + startOffset.width / 2;
	var y1 = startOffset.top + startOffset.height / 2;
	// top right
	var x2 = endOffset.left + endOffset.width / 2;
	var y2 = endOffset.top + endOffset.height / 2;
	// distance
	var length = Math.sqrt(((x2 - x1) * (x2 - x1)) + ((y2 - y1) * (y2 - y1)));
	if (length === 0 && attempt < 3) {
		// If the length is 0, this probably means that neither of the ends appear on the page yet, so wait a bit.
		setTimeout(this._updatePosition.bind(this, ends, attempt + 1), 100);
	}
	// centre
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