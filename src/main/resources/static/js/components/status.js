var JStatusProto = Object.create(HTMLElement.prototype);

JStatusProto.createdCallback = function() {

	this._statusElementName = this.getAttribute('wrapperElement');
	this._showAll = this.getAttribute('showAll');
	this._removeOldChildren = this.getAttribute('removeOldChildren');
	var destination = this.getAttribute('destination');

	/**
	 * Attributes to set on the status elements when they are created.
	 * @private
	 * @type {Object}
	 */
	this._wrapperAttributes = JSON.parse(this.getAttribute('data-wrapperAttributes'));

	var initialData = JSON.parse(this.getAttribute('data-initial'));
	this._setStatus(initialData && initialData.payload);

	socket.subscribe(destination, this._updateStatus.bind(this));
};

JStatusProto._updateStatus = function(statuses) {
	this.classList.add('updated');
	clearTimeout(this._errorTimeout);
	this._errorTimeout = setTimeout(() => this.classList.remove('updated'), 500);

	this._setStatus(statuses);
};

JStatusProto._setStatus = function(statuses) {
	if (!statuses && !this._showAll) {
		return;
	}
	if (!(statuses instanceof Array)) {
		statuses = [statuses];
	}
	this._ensureEnoughElements(statuses.length);
	for (var i = 0; i < this.childNodes.length; i++) {
		var element = this.childNodes[i];
		var status = statuses[i];
		if (status) {
			this._updateElement(element, status);
		} else if (this._showAll) {
			element.innerHTML = '&nbsp;'
		} else if (this._removeOldChildren) {
			// Remove the element.
			this.removeChild(element);
		} else {
			// Hide the element.
			element.style.display = 'none';
		}
	}
};

JStatusProto._createStatusElement = function() {
	var newElement = createElement(this._statusElementName, this._wrapperAttributes);
	newElement.id = `${this.id}_${this.childNodes.length}`;
	this.appendChild(newElement);
};

JStatusProto._ensureEnoughElements = function(number) {
	var elementsNeeded = number - this.childNodes.length;
	if (elementsNeeded > 0) {
		for (var i = 0; i < elementsNeeded; i++) {
			this._createStatusElement();
		}
	}
};

JStatusProto._updateElement = function(element, status) {
	// Show the element.
	element.style.display = '';
	if (typeof status === 'string') {
		element.textContent = status;
	} else if (status && typeof status === 'object' && typeof status.text === 'string') {
		element.textContent = status.text;
	}
	
	if (status && typeof status === 'object' && typeof status.id === 'string') {
		element.id = status.id;
	}
	
	var attributeName = this.getAttribute('attributeName');
	if (attributeName) {
		var statusString = JSON.stringify(status);
		var attributeWrapper = this.getAttribute('attributeWrapper');
		if (attributeWrapper) {
			statusString = attributeWrapper.replace(/\$status/g, statusString);
		}
		element.setAttribute(attributeName, statusString);
	}
};

/**
 * A JStatus is an element that listens to updates on a STOMP destination and manages its child elements with the data
 * received from that destination.  If the data returned is an array, it spawns/hides children per array element.  If
 * the data is not an array, it is treated as a singleton array.
 *
 * When it has spawned/hidden the appropriate children, it passes the corresponding array element into each child's
 * <code>data-status</code> attribute and updates the child's text content if the array element is a string or is an
 * object with a <code>text</code> string field.
 *
 * Attributes:<ul>
 *     <li><code>destination</code>: the STOMP destination to subscribe to.</li>
 *     <li><code>wrapperElement</code>: the name of the element to use as the child elements.  This should be the tag
 *         name for standard elements and the JavaScript class name for custom elements.</li>
 *     <li><code>data-wrapperAttributes</code>: when creating a child element, an object to use whose keys and values
 *         will be the attributes and values to set on the child element.</li>
 *     <li><code>attributeName</code>: an attribute name to set on each child element with the status for that element.
 *         </li>
 *     <li><code>attributeWrapper</code>: a JSON string where <code>$status</code> will be replaced with the status
 *         for that element.  The resulting JSON string will be used as the value for <code>attributeName</code>.</li>
 *     <li><code>data-initial</code>: an object for the initial data for the status.</li>
 *     <li><code>showAll</code>: instead of deleting or hiding children, keep them around but clear their text.</li>
 *     <li><code>removeOldChildren</code>: instead of hiding or emptying children, delete the children.</li>
 * </ul>
 */
var JStatus = document.registerElement('j-status', {
	prototype: JStatusProto
});