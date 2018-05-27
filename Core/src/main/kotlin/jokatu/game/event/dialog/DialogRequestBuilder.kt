package jokatu.game.event.dialog

import jokatu.game.input.Input
import jokatu.game.player.Player
import ophelia.function.ExceptionalBiConsumer
import kotlin.reflect.KClass

/**
 * The beginning of sequence of builders that eventually result in a [DialogRequest] being sent to a player.
 * @param <P> the type of player to be presented with the dialog
 */
class DialogRequestBuilder<P : Player> private constructor(private val player: P) : DialogRequestBuilderNeedingTitle<P> {

	override fun withTitle(title: String): DialogRequestBuilderNeedingMessage<P> {
		return NeedingMessage(title)
	}

	private inner class NeedingMessage internal constructor(private val title: String) : DialogRequestBuilderNeedingMessage<P> {

		override fun withMessage(message: String): DialogRequestBuilderNeedingInputType<P> {
			return NeedingInputType(message)
		}

		private inner class NeedingInputType internal constructor(private val message: String) : DialogRequestBuilderNeedingInputType<P> {

			override fun <I : Input> withInputType(inputType: KClass<I>): DialogRequestBuilderNeedingConsumer<P, I> {
				return NeedingConsumer(inputType)
			}

			private inner class NeedingConsumer<I : Input> internal constructor(private val inputType: KClass<I>) : DialogRequestBuilderNeedingConsumer<P, I> {

				override fun withConsumer(consumer: ExceptionalBiConsumer<in I, in P, *>): MainDialogRequestBuilder<P, I> {
					return BaseDialogRequestBuilder(inputType, player, title, message, consumer)
				}
			}
		}
	}

	companion object {
		fun <P : Player> forPlayer(player: P): DialogRequestBuilder<P> {
			return DialogRequestBuilder(player)
		}
	}
}
