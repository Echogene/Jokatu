layout 'layouts/main.tpl', true,

	pageTitle: 'Playground for events',

	headers: contents {
		script(type: 'text/javascript', src: '/js/events.js') {}
	},

	mainBody: contents {
		div('Play with events here.')
		button(onclick: 'requestEvent()', 'Request event')
		form(action: '/logout', method: 'post') {
			input(type: 'hidden', name: "${_csrf.parameterName}", value: "${_csrf.token}")
			button(id: 'submit', type: 'submit') {yield 'Log out'}
		}
	}