createElement = function(elementName, attributes) {
	var firstChar = elementName.charAt(0);
	var newElement;
	if (firstChar == firstChar.toUpperCase()) {
		// If the first character is uppercase, assume we want to wrap a JavaScript object.
		newElement = new window[elementName];
	} else {
		// Otherwise, we want to wrap a normal element.
		newElement = document.createElement(elementName);
	}
	if (attributes) {
		Object.keys(attributes).forEach(key => {
			var attribute = attributes[key];
			if (attribute instanceof Object) {
				attribute = JSON.stringify(attribute);
			}
			newElement.setAttribute(key, attribute);
		});
	}
	return newElement;
};