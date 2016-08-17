layout 'layouts/main.tpl', true,

	pageTitle: 'Log in',

	headers: contents {
		link(rel: 'stylesheet', href: '/css/login.css')
	},

	mainBody: contents {
		form(name: 'f', action: '/login', method: 'post') {
			input(type: 'hidden', name: "${_csrf.parameterName}", value: "${_csrf.token}")
			div() {
				label(for: 'username', 'Username:')
				input(id: 'username', name: 'username', type: 'text', autocomplete: 'username', autofocus: 'true')
			}
			div() {
				label(for: 'password', 'Password:')
				input(id: 'password', name: 'password', type: 'password', autocomplete: 'current-password')
			}
			div() {
				button(id: 'submit', type: 'submit', 'Log in')
			}
		}
	}
