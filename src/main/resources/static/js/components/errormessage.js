/**
 * @typedef {{
 *     className: string,
 *     methodName: string,
 *     fileName: string,
 *     lineNumber: number
 * }}
 */
let StackTraceElement;
/**
 * @typedef {{
 *     message: string,
 *     localisedMessage: string,
 *     stackTrace: Array.<StackTraceElement>
 *     cause: Error|null
 * }}
 */
let Error;
/**
 * @typedef {{
 *     headers: Object,
 *     payload: Error
 * }}
 */
let ErrorMessage;

class JErrorMessage extends HTMLButtonElement {
	constructor() {
		super();

		observeAttributes(this, new Map([
			['data-message', this._updateMessage.bind(this)]
		]));

		this.addEventListener('click', this._showStacktrace.bind(this));
	};

	/**
	 * @param {ErrorMessage} message
	 */
	_updateMessage(message) {
		if (!message) {
			return;
		}
		this.textContent = message.payload.message;
	};

	_showStacktrace() {
		/**
		 * @type ErrorMessage
		 */
		const message = JSON.parse(this.getAttribute('data-message'));
		if (!message) {
			return;
		}
		let error = message.payload;

		let causes = 0;
		let stacktrace = '';
		do {
			if (causes > 0) {
				stacktrace += `Caused by:\n`
			}
			stacktrace += `${error.message} \n`;
			stacktrace += JErrorMessage._getStackTrace(error);
		} while ((error = error.cause) && (causes++ < 10));

		alert(stacktrace);
	};

	/**
	 * @param {Error} error
	 * @returns {string}
	 * @private
	 */
	static _getStackTrace(error) {
		return error.stackTrace.reduce((previousValue, el) => {
			return previousValue + `\tat ${el.className}.${el.methodName}(${el.fileName}:${el.lineNumber})\n`
		}, "");
	};
}

customElements.define('j-error', JErrorMessage, {extends: 'button'});