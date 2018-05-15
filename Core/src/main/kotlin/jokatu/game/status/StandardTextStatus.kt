package jokatu.game.status

import com.fasterxml.jackson.annotation.JsonValue
import jokatu.game.event.StandardStatusUpdateEvent
import jokatu.game.event.StatusUpdateEvent
import ophelia.event.observable.AbstractSynchronousObservable

import java.text.MessageFormat

/**
 * A [Status] that fires a [StatusUpdateEvent] whenever its text is updated.
 * @author steven
 */
class StandardTextStatus : AbstractSynchronousObservable<StatusUpdateEvent>(), Status {

	@get:JsonValue
	override var text: String? = null

	fun setText(text: String, vararg arguments: Any) {
		this.text = MessageFormat.format(text, *arguments)
		fireEvent(StandardStatusUpdateEvent(this))
	}
}
