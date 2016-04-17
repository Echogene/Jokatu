import static jokatu.util.Json.serialise

layout 'layouts/main.tpl', true,

	pageTitle:"Error log for $username in $gameId",

	headers: contents {
		include template: 'components/messagebox.tpl'
		include template: 'components/errormessage.tpl'
	},

	mainBody: contents {
		yieldUnescaped markupGenerator.bindUserHistory(
				tag: "j-message-box",
				id: "errors",
				wrapperElement: "JErrorMessage",
				destination: "/user/topic/errors.game.${gameId}",
				user: "${username}"
		)
	}