var source = new WebSocket('ws://' + window.location.host + '/test');

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