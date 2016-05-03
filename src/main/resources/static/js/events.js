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
		mutations.map(mutation => ({
					mutation: mutation,
					listener: attributeListeners.get(mutation.attributeName)
			}))
			.filter(o => typeof o.listener != 'undefined')
			.forEach(o => o.listener(o.mutation));
	});
	observer.observe(element, { attributes: true });

	// Trigger the listeners now.
	attributeListeners.forEach(listener => listener());
}