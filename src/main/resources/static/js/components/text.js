var JTextProto = Object.create(HTMLElement.prototype);

JTextProto.createdCallback = function() {

	var template = document.querySelector('#text_template');
	var clone = document.importNode(template.content, true);

	var textField = clone.querySelector('input');

	var submitButton = clone.querySelector('button');
	submitButton.textContent = this.getAttribute('submitName') || 'Submit';

	textField.addEventListener('keypress', e => {
		if (e.keyCode == 13) {
			submitButton.click();
		}
	});

	submitButton.addEventListener('click', () => {
		submitButton.classList.add('submitting');
		socket.send(this.getAttribute('destination'), textField.value)
			.then(() => submitButton.classList.remove('submitting'));
	});

	this.createShadowRoot().appendChild(clone);
};

var JText = document.registerElement('j-text', {
	prototype: JTextProto
});