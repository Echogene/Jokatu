package jokatu.components.controllers.game

import jokatu.components.config.FactoryConfiguration.GameFactories
import jokatu.components.config.InputDeserialisers
import jokatu.components.dao.GameDao
import jokatu.components.exceptions.StandardExceptionHandler
import jokatu.components.markup.MarkupGenerator
import jokatu.game.Game
import jokatu.game.GameID
import jokatu.game.exception.GameException
import jokatu.game.input.Input
import jokatu.game.player.Player
import ophelia.exceptions.maybe.Maybe
import ophelia.exceptions.maybe.Maybe.wrap
import ophelia.exceptions.maybe.MaybeCollectors.toUniqueSuccess
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.messaging.Message
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageExceptionHandler
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.view.RedirectView
import java.security.Principal

/**
 * A big controller (that should probably be several) that controls client requests for games.
 * @author Steven Weston
 */
@Controller
class GameController @Autowired
constructor(
		private val inputDeserialisers: InputDeserialisers,
		private val gameFactories: GameFactories,
		private val gameDao: GameDao,
		private val standardExceptionHandler: StandardExceptionHandler,
		private val markupGenerator: MarkupGenerator
) {

	@RequestMapping("/game/{identity}")
	@Throws(GameException::class)
	internal fun game(@PathVariable("identity") identity: GameID, principal: Principal): ModelAndView {
		val game = gameDao.read(identity) ?: return ModelAndView(RedirectView("/game/0"))
		val viewResolver = gameFactories.getViewResolver(game)
		val player = getPlayer(game, principal.name)
		val modelAndView = if (game.hasPlayer(player.name)) {
			viewResolver.getViewForPlayer(player)
		} else {
			viewResolver.viewForObserver
		}
		modelAndView.addObject("markupGenerator", markupGenerator)
		modelAndView.addObject("username", principal.name)
		modelAndView.addObject("player", player)
		modelAndView.addObject("otherPlayers", game.getOtherPlayers(player.name))
		modelAndView.addObject("gameId", identity)
		modelAndView.addObject("gameName", game.gameName)
		modelAndView.addObject("gameNames", gameFactories.gameNames)
		return modelAndView
	}

	@MessageMapping("/topic/input.game.{identity}")
	@Throws(GameException::class)
	fun input(@DestinationVariable("identity") identity: GameID, @Payload json: Map<String, Any>, principal: Principal) {

		val game = gameDao.read(identity) ?: throw GameException(
				identity,
				"Game with ID $identity does not exist.  You can't input to a game that does not exist."
		)

		val player = getPlayer(game, principal.name)

		val currentStage = game.currentStage ?: throw GameException(identity, "The game hasn't started yet.")
		val acceptedInputs = currentStage.acceptedInputs
		val input: Input = if (acceptedInputs.isEmpty()) {
			object : Input {}
		} else {
			acceptedInputs.stream()
					.map { inputDeserialisers.getDeserialiser(it) }
					.map<Maybe<Input>>(wrap { deserialiser -> deserialiser!!.deserialise(json) })
					.collect(toUniqueSuccess<Input>())
					.returnOnSuccess()
					.throwMappedFailure { e -> GameException(identity, e, "Could not deserialise '$json'.") }
		}
		game.accept(input, player)
	}

	private fun getPlayer(game: Game<*>, name: String): Player {
		val player = game.getPlayerByName(name)
		return when (player) {
			null -> {
				val factory = gameFactories.getPlayerFactory(game)
				factory.produce(game, name)
			}
			else -> player
		}
	}

	@MessageExceptionHandler(Exception::class)
	internal fun handleException(e: Exception, originalMessage: Message<*>, principal: Principal) {
		standardExceptionHandler.handleMessageException(e, originalMessage, principal)
	}

	@ExceptionHandler(GameException::class)
	@ResponseStatus(INTERNAL_SERVER_ERROR)
	@ResponseBody
	internal fun handleException(e: Exception): Exception {
		return e
	}
}
