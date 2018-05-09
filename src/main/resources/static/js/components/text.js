class JText extends HTMLElement {
	connectedCallback() {
		if (!this._alreadySetUp) {
			const template = document.querySelector('#text_template');
			const clone = document.importNode(template.content, true);

			const textField = clone.querySelector('input');

			const submitButton = clone.querySelector('button');
			submitButton.textContent = this.getAttribute('submitLabel') || 'Submit';

			textField.addEventListener('keypress', e => {
				if (e.keyCode == 13) {
					submitButton.click();
				}
			});

			submitButton.addEventListener('click', () => {
				submitButton.classList.add('submitting');
				socket.send(this.getAttribute('destination'), {text: textField.value})
					.then(() => submitButton.classList.remove('submitting'));
			});

			this.appendChild(clone);
		}
		this._alreadySetUp = true;
	}
}

customElements.define('j-text', JText);