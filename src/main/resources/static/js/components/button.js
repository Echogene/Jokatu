var JButtonProto = Object.create(HTMLButtonElement.prototype);

JButtonProto.createdCallback = function() {

	this.addEventListener('click', () => {
		this.classList.add('submitting');
		socket.send(this.getAttribute('destination'), JSON.parse(this.getAttribute('data-input')))
			.then(() => this.classList.remove('submitting'))
			.catch((error, headers) => {
				this.classList.add('error');
				clearTimeout(this._errorTimeout);
				this._errorTimeout = setTimeout(() => this.classList.remove('error'), 1000);

				var popup = new JPopup();
				popup.id = headers.get('receipt-id');
				popup.setAttribute('data-title', 'Error');
				popup.setAttribute('data-message', error.message);
				popup.setAttribute('data-cover', this.id);
				popup.initialise();
			});
	});
};

var JButton = document.registerElement('j-button', {
	prototype: JButtonProto,
	extends: 'button'
});