/**
 * A standard asynchronous get request that expects json back.
 * @param {string} location           the location on the server to get from
 * @param {function(Object)} onload   a function that accepts the returned json
 * @param {function(Object)=} onerror a function that accepts an exception when an error occurs on the server
 */
function get(location, onload, onerror) {
	var request = _defaultRequest('get', location, onload, onerror);
	request.send();
}

/**
 * A standard asynchronous post request that expects json back.
 * @param {string} location           the location on the server to get from
 * @param {Object} params             the parameters of the post
 * @param {function(Object)} onload   a function that accepts the returned json
 * @param {function(Object)=} onerror a function that accepts an exception when an error occurs on the server
 */
function post(location, params, onload, onerror) {
	var request = _defaultRequest('post', location, onload, onerror);
	request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
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

function _defaultRequest(method, location, onload, onerror) {
	var request = new XMLHttpRequest();
	request.open(method, location, true);
	request.setRequestHeader(_csrf_header, _csrf);
	request.responseType = 'json';
	request.onreadystatechange = () => {
		if (request.readyState === 4) {
			if (request.status === 200) {
				onload(request.response)
			} else if (request.status === 500) {
				if (onerror) {
					onerror(request.response);
				} else {
					throw request.response;
				}
			}
		}
	};
	return request;
}

function requestEvent() {
	var request = new XMLHttpRequest();
	request.open("post", "/requestEvent", true);
	request.setRequestHeader(_csrf_header, _csrf);
	request.send();
}