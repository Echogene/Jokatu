var JMessageBoxProto = Object.create(HTMLElement.prototype);

JMessageBoxProto.createdCallback = function() {

	var template = document.querySelector('#message_box_template');
	var clone = document.importNode(template.content, true);

	var messageElementName = this.getAttribute('wrapperElement');
	var destination = this.getAttribute('destination');

	var container = clone.querySelector('div');

	socket.subscribe(destination, (body, headers) => {
		var element = document.createElement(messageElementName);
		element.className = 'message';
		if (typeof body === 'string') {
			element.textContent = body;
		} else {
			element.setAttribute('data-message', JSON.stringify(body));
		}
		container.appendChild(element);
	});

	this.createShadowRoot().appendChild(clone);
};

var JMessageBox = document.registerElement('j-message-box', {
	prototype: JMessageBoxProto
});