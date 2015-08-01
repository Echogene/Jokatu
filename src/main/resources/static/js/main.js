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

/**
 * A standard asynchronous post request that expects json back.
 * @param {string} location         the location on the server to get from
 * @param {Object} params           the parameters of the post
 * @param {function(Object)} onload a function that accepts the returned json
 */
function post(location, params, onload) {
	var request = new XMLHttpRequest();
	request.open('post', location, true);
	request.setRequestHeader(_csrf_header, _csrf);
	request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	request.responseType = 'json';
	request.onload = function() {
		onload(this.response);
	};
	var data = "";
	for (var key in params) {
		data += `${key}=${JSON.stringify(params[key])}&`
	}
	if (data.length > 0) {
		request.send(data.substring(0, data.length - 1));
	} else {
		request.send();
	}
}

function requestEvent() {
	var request = new XMLHttpRequest();
	request.open("post", "/requestEvent", true);
	request.setRequestHeader(_csrf_header, _csrf);
	request.send();
}