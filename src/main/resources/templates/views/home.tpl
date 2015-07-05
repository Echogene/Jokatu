layout 'layouts/main.tpl',

pageTitle: 'Hello world!',

mainBody: contents {
	div('This is the body.')
	button(onclick: 'requestEvent()') {
		div('Request event')
	}
	button(onclick: 'navigator.id.request()') {
		div('Sign in')
	}
}
