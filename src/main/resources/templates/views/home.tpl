layout 'layouts/main.tpl',

pageTitle: 'Hello world!',

mainBody: contents {
	div('This is the body.')
	button(onclick: 'requestEvent()') {
		yield 'Request event'
	}
	button(onclick: 'navigator.id.request()') {
		yield 'Sign in'
	}
}
