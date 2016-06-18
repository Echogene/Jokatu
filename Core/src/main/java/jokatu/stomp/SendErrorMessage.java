package jokatu.stomp;

import ophelia.util.MapUtils;
import org.springframework.messaging.support.ErrorMessage;

/**
 * An error message returned to the client when one of its SEND commands is rejected.
 */
public class SendErrorMessage extends ErrorMessage {
	public SendErrorMessage(Throwable e, String receipt) {
		super(e, MapUtils.createMap("receipt-id", receipt));
	}
}
