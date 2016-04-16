/**
 * @typedef {{
 *     message: string,
 *     localisedMessage: string,
 *     stackTrace: Array
 *     cause: Error|null
 * }}
 */
Error;

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
	/**
	 * @type Error
	 */
	var error = JSON.parse(this.getAttribute('data-message'));
	if (!error) {
		return;
	}

	var causes = 0;
	var stacktrace = '';
	do {
		if (causes > 0) {
			stacktrace += `Caused by:\n`
		}
		stacktrace += `${error.message} \n`;
		stacktrace += this._getStackTrace(error);
	} while ((error = error.cause) && (causes++ < 10));

	alert(stacktrace);
};

/**
 * @param {Error} error
 * @returns {string}
 * @private
 */
JErrorMessageProto._getStackTrace = function(error) {
	return error.stackTrace.reduce((previousValue, el) => {
		return previousValue + `\tat ${el.className}.${el.methodName}(${el.fileName}:${el.lineNumber})\n`
	}, "");
};

var JErrorMessage = document.registerElement('j-error', {
	prototype: JErrorMessageProto,
	extends: 'button'
});