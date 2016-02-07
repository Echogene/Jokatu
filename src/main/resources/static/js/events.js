//todo: Events disappear if fired between one EventSource's timing out and the next's beginning.
//todo: If nothing happens for one EventSource before it times out, the server throws an error.
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
	request.open("post", "/requestEvent", true);
	request.setRequestHeader(_csrf_header, _csrf);
	request.send();
}