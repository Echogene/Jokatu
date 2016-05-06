yieldUnescaped '<!DOCTYPE html>'
html {
	head {
		title(pageTitle)
		link(rel: 'stylesheet', href: '/css/main.css')
		script(type: 'text/javascript',
				"_csrf = '${_csrf.token}', _csrf_header = '${_csrf.headerName}', username = '${username}';")
		script(type: 'text/javascript', src: '/js/stomp.js') {}
		script(type: 'text/javascript', src: '/js/request.js') {}
		script(type: 'text/javascript', src: '/js/events.js') {}
		headers()
	}
	body {
		mainBody()
	}
}