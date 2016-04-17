package jokatu.stomp;

import ophelia.util.MapUtils;
import org.springframework.messaging.support.ErrorMessage;

public class SubscriptionErrorMessage extends ErrorMessage {
	public SubscriptionErrorMessage(Throwable e, String id) {
		super(e, MapUtils.createMap("subscribe-id", id));
	}
}
