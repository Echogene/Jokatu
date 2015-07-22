/**
 * A standard asynchronous get request that expects json back.
 * @param {string} location         the location on the server to get from
 * @param {function(Object)} onload a function that accepts the returned json
 */
function get(location, onload) {
	var request = new XMLHttpRequest();
	request.open('get', location, true);
	request.setRequestHeader(_csrf_header, _csrf);
	request.responseType = 'json';
	request.onload = function() {
		onload(this.response);
	};
	request.send();
}