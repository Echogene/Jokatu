yieldUnescaped '<!DOCTYPE html>'
html {
	head {
		title(pageTitle)
		link(rel: 'stylesheet', href: '/css/main.css')
		script(type: 'text/javascript', "_csrf = '${_csrf.token}', _csrf_header = '${_csrf.headerName}';")
		script(type: 'text/javascript', src: '/js/main.js') {}
		headers()
	}
	body {
		mainBody()
	}
}