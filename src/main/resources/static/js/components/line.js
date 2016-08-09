var JLineProto = Object.create(HTMLDivElement.prototype);

JLineProto.attachedCallback = function() {
	this._endsObserver = new MutationObserver(this._updatePositionFromEnds.bind(this));

	window.addEventListener('load', this._updatePositionFromEnds.bind(this));

	window.addEventListener('optimizedResize', this._updatePositionFromEnds.bind(this), false);

	observeAttributes(this, new Map([
		['data-ends', this._updatePosition.bind(this)]
	]));

	this.innerHTML = '&nbsp;';
};

JLineProto.setEnds = function(start, end) {
	this.setAttribute('data-ends', JSON.stringify({start: start.id, end: end.id}));
};

JLineProto._updatePositionFromEnds = function() {
	this._updatePosition(JSON.parse(this.getAttribute('data-ends')));
};

JLineProto._updatePosition = function(ends, mutation, attempt = 0) {
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
	if ((length < 0.001) && (attempt < 3)) {
		// If the length is 0, this probably means that neither of the ends appear on the page yet, so wait a bit.
		setTimeout(this._updatePosition.bind(this, ends, mutation, attempt + 1), 100);
		return;
	}
	// centre
	var totalOffset = this._getTotalOffsetOfParents();
	var cx = ((x1 + x2) / 2) - (length / 2) - totalOffset[0];
	var cy = ((y1 + y2) / 2) - (this.offsetHeight / 2) - totalOffset[1];
	// angle
	var angle = Math.atan2((y1 - y2), (x1 - x2)) * (180 / Math.PI);

	/*var ss = document.styleSheets;
	var links = '';
	for (var i = 0, max = ss.length; i < max; i++) {
		links += ' ' + ss[i].href;
	}
	console.log(`${links} ${document.readyState}
start left: ${startOffset.left}, start top: ${startOffset.top}, start width: ${startOffset.width}, start height: ${startOffset.height}
end left: ${endOffset.left}, end top: ${endOffset.top}, end width ${endOffset.width}, end height: ${endOffset.height}
left: ${cx}, top: ${cy}, length: ${length}, angle: ${angle}, attempt: ${attempt}`);*/

	this.style.left = `${cx}px`;
	this.style.top = `${cy}px`;
	this.style.width = `${length}px`;
	this.style.transform = `rotate(${angle}deg)`;
};

JLineProto._getTotalOffsetOfParents = function() {
	var element = this.offsetParent;
	var left = 0;
	var top = 0;
	do {
		left += element.offsetLeft;
		top += element.offsetTop;
	} while (element = element.offsetParent);
	return [left, top];
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