package jokatu.stomp;

import ophelia.util.MapUtils;
import org.springframework.messaging.support.ErrorMessage;

/**
 * An error message returned to the client when one of its SUBSCRIBE commands is rejected.
 */
public class SubscriptionErrorMessage extends ErrorMessage {
	public SubscriptionErrorMessage(Throwable e, String id) {
		super(e, MapUtils.createMap("subscribe-id", id));
	}
}
