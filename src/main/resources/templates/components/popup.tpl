include template: 'components/line.tpl'
script(type: 'text/javascript', src: '/js/components/popup.js') {}
link(rel: 'stylesheet', href: '/css/components/popup.css')
template(id: 'popup_template') {
	div(class: 'titleBar') {}
	div(class: 'message') {}
}