class JLine extends HTMLDivElement {
	constructor() {
		super();

		this._endsObserver = new MutationObserver(this._updatePositionFromEnds.bind(this));

		window.addEventListener('load', this._updatePositionFromEnds.bind(this));

		window.addEventListener('optimizedResize', this._updatePositionFromEnds.bind(this), false);

		observeAttributes(this, new Map([
			['data-ends', this._updatePosition.bind(this)]
		]));
	};

	connectedCallback() {
		if (!this._alreadySetUp) {
			this.innerHTML = '&nbsp;';

			this.classList.add('line');
		}
		this._alreadySetUp = true;
	}

	setEnds(start, end) {
		this.setAttribute('data-ends', JSON.stringify({start: start.id, end: end.id}));
	};

	_updatePositionFromEnds() {
		this._updatePosition(JSON.parse(this.getAttribute('data-ends')));
	};

	_updatePosition(ends, mutation, attempt = 0) {
		const startElement = ends && ends.start && document.getElementById(ends.start);
		const endElement = ends && ends.end && document.getElementById(ends.end);

		const endElementsChanged = (startElement != this._startElement) || (endElement != this._endElement);
		if (endElementsChanged) {
			this._endsObserver.disconnect();
		}

		this._startElement = startElement;
		this._endElement = endElement;
		if (!startElement || !endElement) {
			return;
		}

		if (endElementsChanged) {
			this._endsObserver.observe(startElement, {attributes: true, attributeFilter: ['style']});
			this._endsObserver.observe(endElement, {attributes: true, attributeFilter: ['style']});

			this._endsObserver.observe(startElement.parentNode, {childList: true});
			if (endElement.parentNode != startElement.parentNode) {
				this._endsObserver.observe(endElement.parentNode, {childList: true});
			}
		}

		const startOffset = JLine._getOffset(startElement);
		const endOffset = JLine._getOffset(endElement);
		// bottom right
		const x1 = startOffset.left + startOffset.width / 2;
		const y1 = startOffset.top + startOffset.height / 2;
		// top right
		const x2 = endOffset.left + endOffset.width / 2;
		const y2 = endOffset.top + endOffset.height / 2;
		// distance
		const length = Math.sqrt(((x2 - x1) * (x2 - x1)) + ((y2 - y1) * (y2 - y1)));
		if ((length < 0.001) && (attempt < 3)) {
			// If the length is 0, this probably means that neither of the ends appear on the page yet, so wait a bit.
			setTimeout(this._updatePosition.bind(this, ends, mutation, attempt + 1), 100);
			return;
		}
		// centre
		const totalOffset = this._getTotalOffsetOfParents();
		const cx = ((x1 + x2) / 2) - (length / 2) - totalOffset[0];
		const cy = ((y1 + y2) / 2) - (this.offsetHeight / 2) - totalOffset[1];
		// angle
		const angle = Math.atan2((y1 - y2), (x1 - x2)) * (180 / Math.PI);

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

	_getTotalOffsetOfParents() {
		let element = this.offsetParent;
		let left = 0;
		let top = 0;
		do {
			left += element.offsetLeft;
			top += element.offsetTop;
		} while (element = element.offsetParent);
		return [left, top];
	};

	static _getOffset(element) {
		const rect = element.getBoundingClientRect();
		return {
			left: rect.left + window.pageXOffset,
			top: rect.top + window.pageYOffset,
			width: rect.width || element.offsetWidth,
			height: rect.height || element.offsetHeight
		};
	};
}

customElements.define('j-line', JLine, {extends: 'div'});