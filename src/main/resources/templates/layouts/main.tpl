yieldUnescaped '<!DOCTYPE html>'
html {
	head {
		title(pageTitle)
		meta(name: '_csrf', content: "${_csrf.token}")
		meta(name: '_csrf_header', content: "${_csrf.headerName}")
		link(rel: 'stylesheet', href: '/css/main.css')
		script(type: 'text/javascript', src: 'https://login.persona.org/include.js') {}
		script(type: 'text/javascript', src: '/js/main.js') {}
	}
	body {
		mainBody()
	}
}