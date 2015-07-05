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
	var xhr = new XMLHttpRequest();
	xhr.open("POST", "/login/persona", true);
	var param = "assertion=" + assertion;
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.setRequestHeader("Content-length", param.length);
	xhr.setRequestHeader("Connection", "close");

	xhr.onreadystatechange = simpleXhrSentinel(xhr);
}

function signoutUser() {
	var xhr = new XMLHttpRequest();
	xhr.open("GET", "/xhr/sign-out", true);
	xhr.send(null);
	xhr.onreadystatechange = simpleXhrSentinel(xhr);
}

navigator.id.watch({
	loggedInUser: 'steven.weston.alpha@gmail.com',
	onlogin: verifyAssertion,
	onlogout: signoutUser
});