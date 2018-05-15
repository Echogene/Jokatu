package jokatu.stomp

import ophelia.util.MapUtils
import org.springframework.messaging.support.ErrorMessage

/**
 * An error message returned to the client when one of its SUBSCRIBE commands is rejected.
 */
class SubscriptionErrorMessage(e: Throwable, id: String) : ErrorMessage(e, MapUtils.createMap<String, Any>("subscribe-id", id))
