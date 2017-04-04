script(type: 'text/javascript', src: '/js/components/graph2.js') {}
template(id: 'graph2-template') {
	svg(class: 'graph', height: '100%', width: '100%', viewBox: '0 0 100 100') {
		g(class: 'nodes') {}
		g(class: 'edges') {}
	}
}