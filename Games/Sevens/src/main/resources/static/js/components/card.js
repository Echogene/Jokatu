var JCardProto = Object.create(JButton.prototype);

JCardProto._getInput = function() {
	return {
		card: JSON.parse(this.getAttribute('data-card')).text
	};
};

var JCard = document.registerElement('j-card', {
	prototype: JCardProto,
	extends: 'button'
});