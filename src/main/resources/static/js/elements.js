createElement = function(elementName, attributes) {
	let newElement;
	const CustomConstructor = customElements.get(elementName);
	if (CustomConstructor) {
		newElement = new CustomConstructor;
	} else {
		newElement = document.createElement(elementName)
	}
	if (attributes) {
		Object.keys(attributes).forEach(key => {
			let attribute = attributes[key];
			if (attribute instanceof Object) {
				attribute = JSON.stringify(attribute);
			}
			newElement.setAttribute(key, attribute);
		});
	}
	return newElement;
};