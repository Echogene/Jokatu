/**
 * An annotated element has a detailed popup when it is clicked.
 */
class JAnnotated extends HTMLElement {
	constructor() {
		super();
		this.listenToClick();
	}

	connectedCallback() {
		if (!this._alreadySetUp) {
			observeAttributes(this, new Map([
				['data-popup', this.updatePopup.bind(this)]
			]));
		}
		this._alreadySetUp = true;
	}

	updatePopup(popup) {
		if (this._popup) {
			this._popup.setAttribute('data-message', popup);
		}
	}

	listenToClick() {
		this.addEventListener('click', () => {
			if (this._popup) {
				this._popup.hide();
			} else {
				this.classList.add('down');
				const popup = this._popup = new JPopup();
				popup.id = `${this.id}_popup`;
				popup.setAttribute('data-title', 'Info');
				popup.setAttribute('data-message', this.getAttribute('data-popup'));
				popup.setAttribute('data-cover', this.id);
				popup.placeInDefaultContainer();
				popup.addEventListener('hide', () => this.onPopupHide());
			}
		});
	}

	onPopupHide() {
		this.classList.remove('down');
		delete this._popup;
	}
}

customElements.define('j-annotated', JAnnotated);