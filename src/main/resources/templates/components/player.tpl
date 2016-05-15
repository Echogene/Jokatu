script(type: 'text/javascript', src: '/js/components/player.js') {}
template(id: 'player_template') {
	style {
		include unescaped: 'static/css/components/player.css'
	}
	span(class: 'status') {}
	span(class: 'name') {}
}