package jokatu.stomp

import ophelia.util.MapUtils
import org.springframework.messaging.support.ErrorMessage

/**
 * An error message returned to the client when one of its SEND commands is rejected.
 */
class SendErrorMessage(e: Throwable, receipt: String) : ErrorMessage(e, MapUtils.createMap<String, Any>("receipt-id", receipt))
