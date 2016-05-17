script(type: 'text/javascript', src: '/js/components/game_entry.js') {}
template(id: 'game_entry_template') {
	style {
		include unescaped: 'static/css/components/game_entry.css'
	}
	span(class: 'id') {}
	a(class: 'name') {}
}