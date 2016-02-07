yieldUnescaped '<!DOCTYPE html>'
html {
	head {
		title(pageTitle)
		link(rel: 'stylesheet', href: '/css/main.css')
		script(type: 'text/javascript',
				"_csrf = '${_csrf.token}', _csrf_header = '${_csrf.headerName}', username = '${SPRING_SECURITY_CONTEXT?.authentication?.principal?.username}';")
		script(type: 'text/javascript', src: '/js/request.js') {}
		headers()
	}
	body {
		mainBody()
	}
}