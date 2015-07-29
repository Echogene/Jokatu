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