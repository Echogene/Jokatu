(function() {
	var throttle = function(parentEvent, throttledEventName, object) {
		object = object || window;
		var running = false;
		var func = function() {
			if (running) { return; }
			running = true;
			requestAnimationFrame(function() {
				object.dispatchEvent(new CustomEvent(throttledEventName));
				running = false;
			});
		};
		object.addEventListener(parentEvent, func);
	};

	throttle("resize", "optimizedResize");
})();

/**
 * @param {HTMLElement} element
 * @param {Map} attributeListeners
 */
function observeAttributes(element, attributeListeners) {
	// Invoke the listeners when the attribute to which they're bound changes.
	var observer = new MutationObserver(mutations => {
		mutations.map(mutation => {
			var attributeValue = element.getAttribute(mutation.attributeName);
			try {
				attributeValue = JSON.parse(attributeValue)
			} catch (e) {}
			return {
				mutation: mutation,
				listener: attributeListeners.get(mutation.attributeName),
				attributeValue: attributeValue
			};
		})
			.filter(o => typeof o.listener != 'undefined')
			.forEach(o => o.listener(o.attributeValue, o.mutation));
	});
	observer.observe(element, { attributes: true });

	// Trigger the listeners now.
	for (var [attribute, listener] of attributeListeners) {
		var attributeValue = element.getAttribute(attribute);
		try {
			attributeValue = JSON.parse(attributeValue)
		} catch (e) {}
		listener(attributeValue);
	}
}