function mixin(Source, Target) {
	// Add all the properties of the mixins to the output class.
	copyProperties(Source, Target);
	copyProperties(Source.prototype, Target.prototype);
}

/**
 * Copy the properties from the source object to the target object.
 */
function copyProperties(source, target) {
	for (const key of Reflect.ownKeys(source)) {
		if (key !== 'constructor' && key !== 'prototype' && key !== 'name') {
			const descriptor = Object.getOwnPropertyDescriptor(source, key);
			Object.defineProperty(target, key, descriptor);
		}
	}
}