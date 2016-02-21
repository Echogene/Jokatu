var JStatusProto = Object.create(HTMLElement.prototype);

JStatusProto.createdCallback = function() {

	var statusElementName = this.getAttribute('wrapperElement');
	var destination = this.getAttribute('destination');

	var element = document.createElement(statusElementName);

	socket.subscribe(destination, (body, headers) => {
		if (typeof body === 'string') {
			element.textContent = body;
		} else {
			element.setAttribute('data-status', JSON.stringify(body));
		}
	});

	this.createShadowRoot().appendChild(element);
};

var JStatus = document.registerElement('j-status', {
	prototype: JStatusProto
});