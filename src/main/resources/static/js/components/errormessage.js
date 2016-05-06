/**
 * @typedef {{
 *     className: string,
 *     methodName: string,
 *     fileName: string,
 *     lineNumber: number
 * }}
 */
var StackTraceElement;
/**
 * @typedef {{
 *     message: string,
 *     localisedMessage: string,
 *     stackTrace: Array.<StackTraceElement>
 *     cause: Error|null
 * }}
 */
var Error;
/**
 * @typedef {{
 *     headers: Object,
 *     payload: Error
 * }}
 */
var ErrorMessage;

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
	/**
	 * @type ErrorMessage
	 */
	var message = JSON.parse(this.getAttribute('data-message'));
	if (!message) {
		return;
	}
	this.textContent = message.payload.message;
};

JErrorMessageProto._showStacktrace = function() {
	/**
	 * @type ErrorMessage
	 */
	var message = JSON.parse(this.getAttribute('data-message'));
	if (!message) {
		return;
	}
	var error = message.payload;

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