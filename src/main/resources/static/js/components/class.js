/**
 * A JClass is an element that listens to updates on a STOMP destination it expects to contain a boolean and gives
 * itself a class if the value at the destination is truthy and otherwise removes from itself that class.
 *
 * Attributes:<ul>
 *     <li><code>destination</code>: the STOMP destination to subscribe to.</li>
 *     <li><code>data-initial</code>: an object for the initial data from the destination.</li>
 *     <li><code>nameOfClass</code>: the class name to add or remove depending on the value at the location.</li>
 * </ul>
 */
class JClass extends HTMLElement {
	connectedCallback() {
		if (!this._alreadySetUp) {
			const initialData = JSON.parse(this.getAttribute('data-initial'));
			this._updateStatus(initialData && initialData.payload);

			const destination = this.getAttribute('destination');
			socket.subscribe(destination, this._updateStatus.bind(this));
		}
		this._alreadySetUp = true;
	}

	_updateStatus(status) {
		if (status) {
			this.classList.add(this.getAttribute('nameOfClass'));
		} else {
			this.classList.remove(this.getAttribute('nameOfClass'));
		}
	};
}

customElements.define('j-class', JClass);