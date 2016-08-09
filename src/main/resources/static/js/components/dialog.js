var JDialogProto = Object.create(JPopup.prototype);

var JDialog = document.registerElement('j-dialog', {
	prototype: JDialogProto
});