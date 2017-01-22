script(type: 'text/javascript', src: '/js/components/form/integer.js') {}
link(rel: 'stylesheet', href: '/css/components/form/integer.css')
template(id: 'integer_template') {
	span(class: 'signText', title: 'Click to invert the direction') {}
	input(type: 'text', pattern: '[\\d]*', inputmode: 'numeric') {}
	span(class: 'spinner') {
		button(class: 'up', '▴')
		button(class: 'down', '▾')
	}
}