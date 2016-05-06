layout 'layouts/main.tpl', true,

	pageTitle: 'Log in',

	headers: contents {
		link(rel: 'stylesheet', href: '/css/login.css')
		script(type: 'text/javascript', src: '/js/login.js') {}
	},

	mainBody: contents {
		form(name: 'f', action: '/login', method: 'post') {
			input(type: 'hidden', name: "${_csrf.parameterName}", value: "${_csrf.token}")

			label(for: 'username') {yield 'Username:'}
			input(id: 'username', name: 'username', type: 'text', autocomplete: 'username', autofocus: 'true')

			label(for: 'password') {yield 'Password:'}
			input(id: 'password', name: 'password', type: 'password', autocomplete: 'current-password')

			button(id: 'submit', type: 'submit') {yield 'Log in'}
		}
	}
