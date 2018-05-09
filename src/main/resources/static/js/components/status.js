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
 *     <li><code>data-defaultAttributes</code>: when creating a child element, an object to use whose keys and values
 *         will be the attributes and values to set on the child element.</li>
 *     <li><code>attributeName</code>: an attribute name to set on each child element with the status for that element.
 *         </li>
 *     <li><code>data-attributeMapping</code>: a map whose keys should be attribute names of the wrapped elements and
 *         whose values should be field names of the status object.  When the status is updated, each of the
 *         mapped attributes of each wrapped element will be populated with the values for the corresponding
 *         (by <code>data-attributeMapping</code>) fields of the status corresponding to the wrapped element.</li>
 *     <li><code>data-initial</code>: an object for the initial data for the status.</li>
 *     <li><code>showAll</code>: instead of deleting or hiding children, keep them around but clear their text.</li>
 *     <li><code>removeOldChildren</code>: instead of hiding or emptying children, delete the children.</li>
 * </ul>
 */
class JStatus extends HTMLElement {
	connectedCallback() {
		if (!this._alreadySetUp) {
			this._statusElementName = this.getAttribute('wrapperElement');
			this._showAll = this.getAttribute('showAll');
			this._removeOldChildren = this.getAttribute('removeOldChildren');
			const destination = this.getAttribute('destination');

			/**
			 * Attributes to set on the status elements when they are created.
			 * @private
			 * @type {Object}
			 */
			this._defaultAttributes = JSON.parse(this.getAttribute('data-defaultAttributes'));

			const initialData = JSON.parse(this.getAttribute('data-initial'));
			this._setStatus(initialData && initialData.payload);

			socket.subscribe(destination, this._updateStatus.bind(this));
		}
		this._alreadySetUp = true;
	}

	_updateStatus(statuses) {
		this.classList.add('updated');
		clearTimeout(this._errorTimeout);
		this._errorTimeout = setTimeout(() => this.classList.remove('updated'), 500);

		this._setStatus(statuses);
	};

	_setStatus(statuses) {
		if (!statuses && !this._showAll) {
			return;
		}
		if (!(statuses instanceof Array)) {
			statuses = [statuses];
		}
		this._ensureEnoughElements(statuses.length);
		for (let i = this.childNodes.length - 1; i >= 0; i--) {
			const element = this.childNodes[i];
			const status = statuses[i];
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

	_createStatusElement() {
		const newElement = createElement(this._statusElementName, this._defaultAttributes);
		newElement.id = `${this.id}_${this.childNodes.length}`;
		this.appendChild(newElement);
	};

	_ensureEnoughElements(number) {
		const elementsNeeded = number - this.childNodes.length;
		if (elementsNeeded > 0) {
			for (let i = 0; i < elementsNeeded; i++) {
				this._createStatusElement();
			}
		}
	};

	_updateElement(element, status) {
		// Show the element.
		element.style.display = '';
		if (typeof status === 'string') {
			element.textContent = status;

		} else if (typeof status === 'number') {
			element.textContent = String(status);

		} else if (status && typeof status === 'object' && typeof status.text === 'string') {
			element.textContent = status.text;
		}

		if (status && typeof status === 'object' && typeof status.id === 'string') {
			element.id = status.id;
		}

		const attributeName = this.getAttribute('attributeName');
		if (attributeName) {
			const statusString = JSON.stringify(status);
			element.setAttribute(attributeName, statusString);
		}
		const attributeMapping = JSON.parse(this.getAttribute('data-attributeMapping'));
		if (attributeMapping) {
			Object.keys(attributeMapping).forEach(attributeName => {
				const statusField = attributeMapping[attributeName];
				let attributeValue = status[statusField];
				if (attributeValue instanceof Object) {
					attributeValue = JSON.stringify(attributeValue);
				}
				if (typeof attributeValue !== 'undefined') {
					element.setAttribute(attributeName, attributeValue);
				}
			});
		}
	};
}

customElements.define('j-status', JStatus);