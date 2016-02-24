var JButtonProto = Object.create(HTMLButtonElement.prototype);

JButtonProto.createdCallback = function() {

	this.addEventListener('click', () => {
		this.classList.add('submitting');
		socket.send(this.getAttribute('destination'), this.getAttribute('data-input'))
			.then(() => this.classList.remove('submitting'));
	});
};

var JButton = document.registerElement('j-button', {
	prototype: JButtonProto,
	extends: 'button'
});