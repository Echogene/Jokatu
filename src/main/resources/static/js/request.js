/**
 * A standard asynchronous get request that expects json back.
 * @param {string} location the location on the server to get from
 * @param {Object=} params  the parameters for the get
 * @returns {Promise}
 */
function get(location, params = {}) {
	return _defaultRequest('get', location + "?" + _getDataString(params));
}

/**
 * A standard asynchronous post request that expects json back.
 * @param {string} location the location on the server to post to
 * @param {Object} params   the parameters of the post
 * @returns {Promise}
 */
function post(location, params) {
	var headers = new Map();
	headers.set("Content-type", "application/x-www-form-urlencoded");

	return _defaultRequest('post', location, headers, _getDataString(params));
}

function _getDataString(params) {
	var data = "";
	for (var key in params) {
		data += `${key}=${JSON.stringify(params[key])}&`
	}
	if (data.length > 0) {
		// Remove the final ampersand.
		data = data.slice(0, -1);
	}
	return data;
}

function _defaultRequest(method, location, headers = new Map(), data) {

	return new Promise(function(resolve, reject) {

		var request = new XMLHttpRequest();

		request.open(method, location, true);

		request.setRequestHeader(_csrf_header, _csrf);
		for (var [key, value] of headers) {
			request.setRequestHeader(key, value);
		}

		request.responseType = 'json';

		request.onreadystatechange = () => {
			if (request.readyState === 4) {

				if (request.status >= 200 && request.status < 300) {
					resolve(request.response);
				} else {
					reject(request.response);
				}
			}
		};

		if (data) {
			request.send(data);
		} else {
			request.send();
		}
	});
}