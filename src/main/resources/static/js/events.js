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