yieldUnescaped '<!DOCTYPE html>'
html {
	head {
		title(pageTitle)
		link(rel: 'stylesheet', href: '/css/main.css')
		script(type: 'text/javascript', src: 'https://login.persona.org/include.js') {}
		script(type: 'text/javascript', src: '/js/main.js') {}
	}
	body {
		mainBody()
	}
}