package jokatu.game.status

import com.fasterxml.jackson.annotation.JsonValue
import jokatu.game.event.StandardStatusUpdateEvent
import jokatu.game.event.StatusUpdateEvent
import ophelia.event.observable.AbstractSynchronousObservable

/**
 * A [Status] that fires a [StatusUpdateEvent] whenever its text is updated.
 * @author steven
 */
class StandardTextStatus : AbstractSynchronousObservable<StatusUpdateEvent>(), Status {

	@get:JsonValue
	override var text: String? = null
		set(value) {
			field = value
			fireEvent(StandardStatusUpdateEvent(this))
		}
}
