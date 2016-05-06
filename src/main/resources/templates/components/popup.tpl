script(type: 'text/javascript', src: '/js/components/popup.js') {}
template(id: 'popup_template') {
	style {
		include unescaped: 'static/css/components/popup.css'
	}
	div(class: 'titleBar') {}
	div(class: 'message') {}
}