var JTextProto = Object.create(HTMLElement.prototype);

JTextProto.createdCallback = function() {
	var shadow = this.createShadowRoot();

	var callback = window[this.getAttribute('onSubmit')];

	var textField = document.createElement('input');
	shadow.appendChild(textField);

	var submitButton = document.createElement('button');
	submitButton.textContent = this.getAttribute('submitName') || 'Submit';
	shadow.appendChild(submitButton);

	textField.addEventListener('keypress', (e) => {
		if (e.keyCode == 13) {
			submitButton.click()
		}
	});
	submitButton.addEventListener('click', () => callback.call(null, textField.value));
};

var JText = document.registerElement('j-text', {
	prototype: JTextProto
});