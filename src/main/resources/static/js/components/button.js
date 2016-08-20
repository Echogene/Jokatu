var JButtonProto = Object.create(HTMLButtonElement.prototype);

JButtonProto.createdCallback = function() {

	this.addEventListener('click', () => {
		this.classList.add('submitting');
		socket.send(this.getAttribute('destination'), this._getInput())
			.then(() => this.classList.remove('submitting'))
			.catch((error, headers) => {
				this.classList.add('error');
				clearTimeout(this._errorTimeout);
				this._errorTimeout = setTimeout(() => this.classList.remove('error'), 1000);

				var popup = new JError();
				popup.id = headers.get('receipt-id');
				popup.setAttribute('data-title', 'Error');
				popup.setAttribute('data-message', error.message || error);
				popup.setAttribute('data-cover', this.id);
				popup.placeInDefaultContainer();
			});
	});
};

/**
 * @return {Object} the JSON object to send to the destination
 * @protected
 */
JButtonProto._getInput = function() {
	return JSON.parse(this.getAttribute('data-input'));
};

var JButton = document.registerElement('j-button', {
	prototype: JButtonProto,
	extends: 'button'
});