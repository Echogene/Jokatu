var JButtonProto = Object.create(HTMLButtonElement.prototype);

JButtonProto.createdCallback = function() {

	this.addEventListener('click', () => {
		this.classList.add('submitting');
		socket.send(this.getAttribute('destination'), JSON.parse(this.getAttribute('data-input')))
			.then(() => this.classList.remove('submitting'))
			.catch(() => {
				this.classList.add('error');
				setTimeout(() => this.classList.remove('error'), 1000);
			});
	});
};

var JButton = document.registerElement('j-button', {
	prototype: JButtonProto,
	extends: 'button'
});