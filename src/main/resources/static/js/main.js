var source = new EventSource("events/exampleStream");

source.onmessage = function(e) {
	var div = document.getElementById('lol');
	if (!div) {
		div = document.createElement('div');
		div.id = 'lol';
		document.body.appendChild(div);
	}
	div.innerHTML = e.data;
};

function requestEvent() {
	var request = new XMLHttpRequest();
	request.open("post", "requestEvent", true);
	request.setRequestHeader(_csrf_header, _csrf);
	request.send();
}

function simpleXhrSentinel(xhr) {
	return function() {
		if (xhr.readyState == 4) {
			if (xhr.status == 200){
				alert('lol');
			}
			else {
				navigator.id.logout();
				alert("XMLHttpRequest error: " + xhr.status);
			}
		}
	}
}

function verifyAssertion(assertion) {
	var request = new XMLHttpRequest();
	request.open("POST", "/login/persona", true);
	var param = "assertion=" + assertion;
	request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	request.setRequestHeader("Content-length", param.length);
	request.setRequestHeader("Connection", "close");
	request.setRequestHeader(_csrf_header, _csrf);
	request.send(param);

	request.onreadystatechange = simpleXhrSentinel(request);
}

function signoutUser() {
	var request = new XMLHttpRequest();
	request.open("GET", "/request/sign-out", true);
	//request.send(null);
	request.onreadystatechange = simpleXhrSentinel(request);
}

navigator.id.watch({
	loggedInUser: 'echogene.alpha@gmail.com',
	onlogin: verifyAssertion,
	onlogout: signoutUser
});