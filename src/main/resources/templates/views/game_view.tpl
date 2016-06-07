layout 'layouts/main.tpl', true,

	pageTitle: "$gameName $gameId",

	headers: contents {
		script(type: 'text/javascript', "gameId = ${gameId};")
		script(type: 'text/javascript', src: '/js/game_main.js') {}
		script(type: 'text/javascript', "socket.subscribe(\"/user/topic/errors.game.${gameId}\");")
		link(rel: 'stylesheet', href: '/css/game.css')
		include template: 'components/messagebox.tpl'
		include template: 'components/status.tpl'
		include template: 'components/player.tpl'
		include template: 'components/class.tpl'
		additionalHeaders()
	},

	mainBody: contents {
		div(id: 'main') {
			div(id: 'sidebar') {
				yieldUnescaped markupGenerator.bindLast(
						tag: 'j-status',
						id: 'status',
						wrapperElement: 'div',
						destination: "/topic/status.game.${gameId}"
				)
				yieldUnescaped markupGenerator.bindHistory(
						tag: 'j-message-box',
						id: 'public-messages',
						wrapperElement: 'div',
						toppost: true,
						destination: "/topic/public.game.${gameId}"
				)
				yieldUnescaped markupGenerator.bindUserHistory(
						tag: 'j-message-box',
						id: 'private-messages',
						wrapperElement: 'div',
						toppost: true,
						destination: "/user/topic/private.game.${gameId}",
						user: "${username}"
				)
				yieldUnescaped markupGenerator.bindLast(
						tag: 'j-status',
						id: 'players',
						wrapperElement: 'JPlayer',
						attributeName: 'data-player',
						removeOldChildren: 'true',
						destination: "/topic/players.game.${gameId}"
				)
				yieldUnescaped markupGenerator.bindLast(
						tag: 'j-status',
						id: 'observers',
						wrapperElement: 'div',
						removeOldChildren: 'true',
						destination: "/topic/observers.game.${gameId}"
				)
			}
			div(id: 'contents') {
				mainContents()
			}
		}
		yieldUnescaped markupGenerator.bindLastStart(
				tag: 'j-class',
				id: 'result-container',
				class: 'overlay',
				nameOfClass: 'ended',
				destination: "/topic/result.game.${gameId}"
		)
			yieldUnescaped markupGenerator.bindLast(
					tag: 'j-status',
					id: 'result',
					wrapperElement: 'div',
					destination: "/topic/result.game.${gameId}"
			)
		yieldUnescaped '</j-class>'
		yieldUnescaped markupGenerator.bindUserLastStart(
				tag: 'j-class',
				id: 'awaiting',
				nameOfClass: 'awaiting',
				destination: "/user/topic/awaiting.game.${gameId}",
				user: "${username}"
		)
			span('You need to provide input.')
		yieldUnescaped '</j-class>'
	}