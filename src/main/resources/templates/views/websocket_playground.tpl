layout 'layouts/main.tpl', true,

	pageTitle: 'Playground for WebSockets',

	headers: contents {
		script(type: 'text/javascript', src: '/js/websocket.js') {}
	},

	mainBody: contents {
		div('Play with WebSockets here.')
		button(onclick: 'requestEvent()', 'Request event')
		div() {
			input(id: 'to_send', type: 'text', value: 'lol') {}
			button(onclick: 'source.send(document.getElementById("to_send").value);', 'Send')
		}
	}