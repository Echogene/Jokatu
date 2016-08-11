var JErrorProto = Object.create(JPopup.prototype);

JErrorProto.createdCallback = function() {
	JPopup.prototype.createdCallback && JPopup.prototype.createdCallback.call(this);

	this._titleBar.addEventListener('mouseup', (e) => {
		if (e.button === 1) {
			this.hide();
		}
	});
};

JErrorProto.placeInDefaultContainer = function() {
	var container = document.getElementById('popup-container');
	if (!container) {
		container = document.createElement('div');
		container.id = 'popup-container';
		container.classList.add('overlay');
		document.body.appendChild(container);
	}
	container.appendChild(this);
};

var JError = document.registerElement('j-error-popup', {
	prototype: JErrorProto
});