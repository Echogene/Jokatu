layout 'layouts/main.tpl', true,

	pageTitle: 'Logged in',

	mainBody: contents {
		div('Your login was successful.')
		form(action: '/logout', method: 'post') {
			input(type: 'hidden', name: "${_csrf.parameterName}", value: "${_csrf.token}")
			button(id: 'submit', type: 'submit') {yield 'Log out'}
		}
	}