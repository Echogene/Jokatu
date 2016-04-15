var JErrorMessageProto = Object.create(HTMLButtonElement.prototype);

JErrorMessageProto.createdCallback = function() {

	var observer = new MutationObserver(mutations => {
		mutations.filter(mutation => mutation.attributeName == 'data-message')
			.forEach(this._updateMessage.bind(this));
	});
	observer.observe(this, { attributes: true });

	this._updateMessage();

	this.addEventListener('click', this._showStacktrace.bind(this));
};

JErrorMessageProto._updateMessage = function() {
	var error = JSON.parse(this.getAttribute('data-message'));
	if (!error) {
		return;
	}
	this.textContent = error.message;
};

JErrorMessageProto._showStacktrace = function() {
	var error = JSON.parse(this.getAttribute('data-message'));
	if (!error) {
		return;
	}
	var stacktrace = "";
	error.stackTrace.forEach(el => {
		stacktrace += `at ${el.className}.${el.methodName}(${el.fileName}:${el.lineNumber})\n`
	});
	alert(stacktrace);
};

var JErrorMessage = document.registerElement('j-error', {
	prototype: JErrorMessageProto,
	extends: 'button'
});