mixin = function(destination, source) {
	for (var prop in source) {
		if (source.hasOwnProperty(prop)) {
			destination[prop] = source[prop];
		}
	}
	return destination;
};