layout 'layouts/main.tpl', true,

	pageTitle: 'Logged in',

	mainBody: contents {
		div('Your login was successful.')
		a(href: "/event_playground") {yield 'Play with events'}
		form(action: '/logout', method: 'post') {
			input(type: 'hidden', name: "${_csrf.parameterName}", value: "${_csrf.token}")
			button(id: 'submit', type: 'submit') {yield 'Log out'}
		}
	}