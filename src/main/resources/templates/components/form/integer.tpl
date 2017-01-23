script(type: 'text/javascript', src: '/js/components/form/integer.js') {}
link(rel: 'stylesheet', href: '/css/components/form/integer.css')
template(id: 'integer_template') {
	span(class: 'signText', title: 'Click to invert the direction') {}
	input(type: 'text', class: 'numericInput', pattern: '[\\d]*', inputmode: 'numeric') {}
	input(type: 'hidden', class: 'valueHolder', inputmode: 'numeric') {}
	span(class: 'spinner') {
		button(class: 'up', type: 'button', '▴')
		button(class: 'down', type: 'button', '▾')
	}
}