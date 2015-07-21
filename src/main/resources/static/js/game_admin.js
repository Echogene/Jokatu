function createGame() {
	var request = new XMLHttpRequest();
	request.open("get", "createGame.do", true);
	request.setRequestHeader(_csrf_header, _csrf);
	request.onload = function() {
		var newGame = JSON.parse(this.responseText);
	};
	request.send();
}