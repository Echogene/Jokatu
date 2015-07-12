layout 'layouts/main.tpl', true,

	pageTitle: 'Logged in',

	mainBody: contents {
		div('Your login was successful.')
		div() {a(href: "/event_playground", 'Play with events')}
		div() {a(href: "/websocket_playground", 'Play with websockets')}
		form(action: '/logout', method: 'post') {
			input(type: 'hidden', name: "${_csrf.parameterName}", value: "${_csrf.token}")
			button(id: 'submit', type: 'submit', 'Log out')
		}
	}